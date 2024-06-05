package com.example.test

import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.test.Adapter.MessageAdapter
import com.example.test.Model.MessageModel
import com.example.test.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    //For Image/Gif
    private var selectedMediaUri: Uri? = null
    private val selectMediaLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedMediaUri = it
                val mimeType = contentResolver.getType(it)
                if (mimeType != null && mimeType.startsWith("image/gif")) {
                    uploadMediaToFirebaseStorage(it, "gif")
                } else {
                    uploadMediaToFirebaseStorage(it, "image")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        //getData(intent.getStringExtra("chatId"))


        // Set the title to the user's name
        val userName = intent.getStringExtra("userName")
        supportActionBar?.title = userName

        verifyChatId()

        binding.imageView4.setOnClickListener {
            if (binding.yourmessage.text!!.isEmpty()) {
                Toast.makeText(this, "please enter your message", Toast.LENGTH_SHORT).show()
            } else {
                storeData(binding.yourmessage.text.toString(), "text")
            }
        }

        // For Image/GIF selection
        binding.textInputLayout.setEndIconOnClickListener {
            selectMediaLauncher.launch("image/*")
        }


        // Setup keyboard visibility listener
        setupKeyboardVisibilityListener()

        // Set padding for the RecyclerView
        setRecyclerViewPadding()

    }


    private var senderId: String? = null
    private var chatId: String? = null
    private var receiverId: String? = null


    private fun verifyChatId() {
        receiverId = intent.getStringExtra("userId")
        senderId = FirebaseAuth.getInstance().currentUser?.phoneNumber
        chatId = senderId + receiverId
        val reverseChatId = receiverId + senderId

        val reference = FirebaseDatabase.getInstance().getReference("Chats")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(chatId!!)) {
                    // Debugging: Check if correct chatId is found
                    Log.d("ChatActivity", "ChatId Found: $chatId")
                    getData(chatId)
                } else if (snapshot.hasChild(reverseChatId)) {
                    chatId = reverseChatId
                    // Debugging: Check if correct chatId is found after reversing
                    Log.d("ChatActivity", "Reverse ChatId Found: $chatId")

                    getData(chatId!!)
                } else {
                    // Debugging: If no chatId is found
                    Log.d("ChatActivity", "ChatId not found")
                    Toast.makeText(this@ChatActivity, "Chat not found", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatActivity, "something wrong", Toast.LENGTH_SHORT).show()
            }

        })

    }


    private fun getData(chatId: String?) {

        FirebaseDatabase.getInstance().getReference("Chats")
            .child(chatId!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val list = arrayListOf<MessageModel>()
                    for (show in snapshot.children) {
                        list.add(show.getValue(MessageModel::class.java)!!)
                    }

                    binding.recyclerView2.adapter = MessageAdapter(this@ChatActivity, list)

                    // Scroll to the bottom
                    binding.recyclerView2.post {
                        binding.recyclerView2.scrollToPosition(list.size - 1)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity, error.message, Toast.LENGTH_SHORT).show()
                }

            })

    }


//    private fun sendMessage(msg: String) {
//
//
//        val reference = FirebaseDatabase.getInstance().getReference("Chats")
//        reference.addListenerForSingleValueEvent(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.hasChild(reverseChatId)){
//                    storeData(reverseChatId,msg,senderId,receiverId)
//                }else {
//                    storeData(chatId,msg,senderId,receiverId)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@ChatActivity, "something wrong", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//
//
//    }


    //To send image/gif
    private fun uploadMediaToFirebaseStorage(mediaUri: Uri, type: String) {
        val fileName = "${System.currentTimeMillis()}.${if (type == "gif") "gif" else "jpg"}"
        val storageReference = FirebaseStorage.getInstance().reference.child("chat_media/$fileName")
        val uploadTask = storageReference.putFile(mediaUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            storageReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.let { uri ->
                    storeData(uri.toString(), type)
                }
            } else {
                Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun storeData(msg: String, type: String) {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime: String = SimpleDateFormat("HH:mm: a", Locale.getDefault()).format(Date())

//        receiverId = intent.getStringExtra("userId")
        val map = hashMapOf<String, String>()
        map["message"] = msg
        map["type"] = type
        map["senderId"] = senderId!!
//        map["receiverId"] = receiverId!!
        map["currentDate"] = currentDate
        map["currentTime"] = currentTime
        val reference = FirebaseDatabase.getInstance().getReference("Chats").child(chatId!!)

        reference.child(reference.push().key!!)
            .setValue(map).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.yourmessage.text = null

                    Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun setupKeyboardVisibilityListener() {
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            val isKeyboardShown = keypadHeight > screenHeight * 0.15

            if (isKeyboardShown) {
//                // Scroll to the bottom when the keyboard is shown
//                binding.recyclerView2.post {
//                    binding.recyclerView2.scrollToPosition(binding.recyclerView2.adapter!!.itemCount - 1)
//                }
                // Scroll to the bottom when the keyboard is shown
                binding.recyclerView2.post {
                    binding.recyclerView2.adapter?.let {
                        binding.recyclerView2.scrollToPosition(it.itemCount - 1)
                    }
                }

            }
        }
    }

    private fun setRecyclerViewPadding() {
        val actionBarHeight = supportActionBar?.height ?: 0
        val statusBarHeight = getStatusBarHeight()

        val topPadding = actionBarHeight + statusBarHeight
        binding.recyclerView2.setPadding(0, topPadding, 0, 0)
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

}