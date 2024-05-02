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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class NewLogin : AppCompatActivity() {


    private lateinit var texttogoSinUp : TextView
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_login)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        texttogoSinUp = findViewById(R.id.go_sign_up)
        btnLogin = findViewById(R.id.btn_login)
        edtEmail =findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        auth = Firebase.auth

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            login(email,password)
        }

        texttogoSinUp.setOnClickListener {
            startActivity(Intent(this, NewSignUP::class.java))
        }

    }


    private fun login(email:String, password : String) {

        if(TextUtils.isEmpty(edtEmail.text.toString())){
            edtEmail.setError("please enter Email")
            return
        }else if(TextUtils.isEmpty(edtPassword.text.toString())) {
            edtPassword.setError("please enter Password")
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //sharedPreferences.edit().putBoolean("is_logged_in", true).apply()
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "some error occured", Toast.LENGTH_SHORT).show()

                }
            }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}