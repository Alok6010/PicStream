package com.example.test

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.test.Adapter.MessageAdapter
import com.example.test.Model.MessageModel
import com.example.test.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ChatActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityChatBinding
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

        verifyChatId()

        binding.imageView4.setOnClickListener {
            if (binding.yourmessage.text!!.isEmpty()){
                Toast.makeText(this, "please enter your message", Toast.LENGTH_SHORT).show()
            }else{
                storeData(binding.yourmessage.text.toString())
            }
        }

    }


    private var senderId : String? = null
    private var chatId : String? = null
    private var receiverId : String? = null

    private fun verifyChatId(){

        receiverId = intent.getStringExtra("userId")
        senderId = FirebaseAuth.getInstance().currentUser?.phoneNumber
        chatId =  senderId+receiverId
        val reverseChatId = receiverId+senderId

        val reference = FirebaseDatabase.getInstance().getReference("Chats")
        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChild(chatId!!)){
                    // Debugging: Check if correct chatId is found
                    Log.d("ChatActivity", "ChatId Found: $chatId")
                    getData(chatId)
                }else if(snapshot.hasChild(reverseChatId)) {
                    chatId = reverseChatId
                    // Debugging: Check if correct chatId is found after reversing
                    Log.d("ChatActivity", "Reverse ChatId Found: $chatId")

                    getData(chatId!!)
                }else{
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
            .child(chatId!!).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val list = arrayListOf<MessageModel>()
                     for (show in snapshot.children){
                         list.add(show.getValue(MessageModel::class.java)!!)
                     }

                    binding.recyclerView2.adapter = MessageAdapter(this@ChatActivity,list)

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

    private fun storeData(msg: String) {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime: String = SimpleDateFormat("HH:mm: a", Locale.getDefault()).format(Date())

//        receiverId = intent.getStringExtra("userId")
        val map = hashMapOf<String, String>()
        map["message"] = msg
        map["senderId"] = senderId!!
//        map["receiverId"] = receiverId!!
        map["currentDate"] = currentDate
        map["currentTime"] = currentTime
        val reference = FirebaseDatabase.getInstance().getReference("Chats").child(chatId!!)

        reference.child(reference.push().key!!)
            .setValue(map).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()
                    binding.yourmessage.text = null
                }else{
                    Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }

}