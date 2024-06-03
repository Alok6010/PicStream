package com.example.test

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.test.auth.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NewSignUP : AppCompatActivity() {

    private lateinit var textTogoLogin: TextView
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var edtConfirmPass: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_sign_up)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }


        textTogoLogin = findViewById(R.id.go_login_page)
        btnSignUp = findViewById(R.id.btn_signup)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtConfirmPass = findViewById(R.id.edt_confirm_pass)
        auth = Firebase.auth

        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val confirmpass = edtConfirmPass.text.toString()
            if (password == confirmpass) {
                SignUp( email, password)
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }

        }



    }


    private fun SignUp( email: String, password: String) {


         if(TextUtils.isEmpty(edtEmail.text.toString())){
            edtEmail.setError("please enter Email")
            return
        }else if(TextUtils.isEmpty(edtPassword.text.toString())){
            edtPassword.setError("please enter Password")
            return
        }else if(TextUtils.isEmpty(edtConfirmPass.text.toString())){
            edtConfirmPass.setError("please enter ConfirmPassword")
            return
        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    startActivity(Intent(this, RegisterActivity::class.java))


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "some error occured", Toast.LENGTH_SHORT).show()
                }
            }


    }



}