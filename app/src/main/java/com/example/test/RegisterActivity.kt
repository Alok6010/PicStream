package com.example.test

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.test.Model.UserModel
import com.example.test.databinding.ActivityRegisterBinding
import com.example.test.utlis.Config
import com.example.test.utlis.Config.hideDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class RegisterActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRegisterBinding
    private var imageUri: Uri? = null

    private val selectImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
        binding.userImage.setImageURI(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding.userImage.setOnClickListener {
            selectImages.launch("image/*")
        }

        binding.termsLink.setOnClickListener {
            startActivity(Intent(this, TnCActivity::class.java))
        }

        binding.SaveData.setOnClickListener {
            validateData()
           // startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun validateData() {
        if(imageUri == null
            || binding.userName.text.toString().isEmpty()
            || binding.userCity.text.toString().isEmpty()
            || binding.userEmail.text.toString().isEmpty()
            || binding.userGender.text.toString().isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else if(!binding.termsAndConditionsCheckbox.isChecked){
            Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show()
        }

        else{
            uploadImage()

        }
    }

    private fun uploadImage() {

        Config.showDialog(this)

        val storageRef = FirebaseStorage.getInstance().getReference("profile")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("profile.jpg")


        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    storeData(it)
                }.addOnFailureListener {
                    hideDialog()
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                hideDialog()
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun storeData(imageUrl : Uri?) {

        val data = UserModel(
            name = binding.userName.text.toString(),
            email = binding.userEmail.text.toString(),
            city = binding.userCity.text.toString(),
            gender = binding.userGender.text.toString(),
            number = FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
            image = imageUrl.toString()
        )


        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!)
            .setValue(data)
            .addOnCompleteListener{
                hideDialog()
                if(it.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                }else{

                    Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }

    }
}