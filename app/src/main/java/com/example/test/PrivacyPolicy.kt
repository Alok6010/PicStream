package com.example.test

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PrivacyPolicy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_privacy_policy)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set the terms and conditions text
        val tvPrivcacyPolicy = findViewById<TextView>(R.id.tvPrivcacyPolicy)
        tvPrivcacyPolicy.text = """
            Introduction

            TestingApp we are committed to protecting and respecting your privacy. This Privacy Policy explains how we collect, use, disclose, and safeguard your information when you visit our website [your website URL], use our services, or interact with us in other ways. Please read this policy carefully to understand our views and practices regarding your personal data and how we will treat it.

1. Information We Collect
We may collect and process the following data about you:

1.1 Information You Provide to Us
Personal Identification Information: such as your name, email address, phone number, etc.
Account Information: if you create an account, we collect your username, password, and other registration details.
Payment Information: including your credit card details, billing address, and other financial data.
1.2 Information We Collect Automatically
Log Data: details of your visits to our site including traffic data, location data, logs, and other communication data.
Device Information: information about your device, such as IP address, browser type, and operating system.
Usage Data: information about how you use our website and services, such as the pages you visit and the links you click.
1.3 Information from Third Parties
We may receive information about you from other sources, including third-party services and partners, and combine it with information we have about you.

2. How We Use Your Information
We use the information we collect in the following ways:

To Provide and Maintain Our Services: including to process your transactions, manage your account, and provide customer support.
To Improve Our Services: analyzing the information to understand user behavior and improve our offerings.
To Communicate with You: sending you notifications, updates, and other information related to your use of our services.
For Marketing Purposes: sending you promotional materials and offers (you can opt-out at any time).
To Ensure Security: monitoring and ensuring the security of our services and preventing fraud.
3. Sharing Your Information
We do not sell, trade, or otherwise transfer your personal information to outside parties except in the following cases:

With Your Consent: we may share your information with third parties if you give us explicit consent.
Service Providers: we may share information with third-party vendors, consultants, and other service providers who work on our behalf.
Legal Requirements: we may disclose your information to comply with applicable laws, regulations, or legal processes.
Business Transfers: in the event of a merger, acquisition, or sale of assets, your information may be transferred to the new entity.
4. Security of Your Information
We use administrative, technical, and physical security measures to protect your personal information. However, please be aware that no method of internet transmission or electronic storage is 100% secure and we cannot guarantee absolute security.

5. Your Data Protection Rights
Depending on your location, you may have the following rights regarding your personal information:

Access: the right to access and obtain a copy of your data.
Correction: the right to correct any inaccurate or incomplete data.
Deletion: the right to request the deletion of your data.
Restriction: the right to restrict the processing of your data.
Objection: the right to object to the processing of your data.
Data Portability: the right to receive your data in a structured, commonly used, and machine-readable format.
To exercise any of these rights, please contact us at alok15082002@gmail.com.

6. Developer Information
For any inquiries related to the development of our services, you can contact our developer:

Name: Alok Mishra
Phone Number: 9260971466
Email: alok15082002@gmail.com
7. Changes to This Privacy Policy
We may update this Privacy Policy from time to time. We will notify you of any changes by posting the new Privacy Policy on this page. You are advised to review this Privacy Policy periodically for any changes. Changes to this Privacy Policy are effective when they are posted on this page.

8. Contact Us
If you have any questions about this Privacy Policy, please contact us:

By Email: alok15082002@gmail.com

            If you have any questions, please contact us at alok15082002@gmail.com.
        """.trimIndent()


    }
}