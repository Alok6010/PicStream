package com.example.test.navdrawer

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.R

class Developer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_developer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set the terms and conditions text
        val tvdeveloperInfo = findViewById<TextView>(R.id.tvdeveloperInfo)
        tvdeveloperInfo.text = """
            Introduction

            This page provides information about the developer responsible for the development and maintenance of this website/application. For any technical inquiries, suggestions, or issues related to the website/application, please reach out to the developer using the contact information provided below.

Developer Profile
Name: Alok Mishra
Phone Number: 9260971466
Email: alok15082002@gmail.com
Role and Responsibilities
As the developer, Alok Mishra is responsible for:

Website/Application Development: Designing, coding, and maintaining the technical infrastructure of the website/application.
Feature Implementation: Developing and implementing new features to enhance user experience.
Bug Fixes: Addressing and resolving technical issues and bugs reported by users.
Security: Ensuring that the website/application is secure and protected against vulnerabilities.
Performance Optimization: Enhancing the performance and efficiency of the website/application.
User Support: Providing technical support to users and addressing their inquiries and concerns.
Feedback and Support
Your feedback is important to us. If you encounter any technical issues or have suggestions for improving the website/application, please contact Alok Mishra using the details provided above. We strive to respond to all inquiries promptly and appreciate your patience.

Commitment to Quality
Alok Mishra is committed to delivering a high-quality and reliable website/application. Continuous improvement and user satisfaction are top priorities. We welcome any feedback that helps us achieve these goals.

Updates and Changes
The developer may update this page from time to time to reflect changes in responsibilities or contact information. Please check this page periodically for updates.

Contact Information
For any technical assistance or inquiries, please contact:

Name: Alok Mishra
Phone Number: 9260971466
Email: alok15082002@gmail.com
        """.trimIndent()

    }
}