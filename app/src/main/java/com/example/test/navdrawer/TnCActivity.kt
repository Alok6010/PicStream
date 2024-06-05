package com.example.test.navdrawer

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.R

class TnCActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tn_cactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set the terms and conditions text
        val tvTermsContent = findViewById<TextView>(R.id.tvTermsContent)
        tvTermsContent.text = """
            Introduction

            Welcome to TestingApp (the "App"). By using the App, you agree to be bound by these Terms and Conditions (the "Terms"). If you do not agree to these Terms, please do not use the App.

User Information
By registering and using the App, you provide us with certain personal information, including but not limited to your username, email, city, gender, phone number, and profile photo (collectively, "User Information").

Collection of Information
Username: Required for identification within the App.
Email: Required for communication and account verification.
City: Helps to connect users from the same location.
Gender: Optional; used for profile information.
Phone Number: Required for account verification and communication.
Profile Photo: Optional; used to visually identify users within the App.
Use of Information
Public Display: Your username, email, and profile photo will be visible to all users of the App. Please ensure you are comfortable with this information being public before providing it.
Communication: Your email and phone number may be used to communicate with you regarding your account or important updates about the App.
Internal Use: Your city and gender information will be used internally to improve user experience and provide better services.
User Conduct
You agree to use the App only for lawful purposes and in a way that does not infringe the rights of, restrict, or inhibit anyone else's use and enjoyment of the App. Prohibited behavior includes but is not limited to:

Harassing or causing distress or inconvenience to any other user.
Transmitting obscene or offensive content.
Disrupting the normal flow of dialogue within the App.
Account Security
You are responsible for maintaining the confidentiality of your account and password and for restricting access to your device. You agree to accept responsibility for all activities that occur under your account or password.

Intellectual Property
All content, trademarks, and data on this App, including but not limited to software, databases, text, graphics, icons, and hyperlinks, are the property of TestingApp or have been legally licensed to TestingApp and are protected by law. Unauthorized use is prohibited.

Termination
We reserve the right to terminate or suspend your access to the App at our sole discretion, without prior notice or liability, for any reason whatsoever, including but not limited to a breach of the Terms.

Limitation of Liability
The App is provided on an "as is" and "as available" basis. We make no warranties, expressed or implied, and hereby disclaim and negate all other warranties, including without limitation, implied warranties or conditions of merchantability, fitness for a particular purpose, or non-infringement of intellectual property or other violation of rights.

Changes to Terms
We reserve the right, at our sole discretion, to modify or replace these Terms at any time. If a revision is material, we will provide at least 30 days' notice prior to any new terms taking effect. What constitutes a material change will be determined at our sole discretion.

Governing Law
These Terms shall be governed and construed in accordance with the laws of Indian Government, without regard to its conflict of law provisions.

Contact Us
If you have any questions about these Terms, please contact us:

Email: alok15082002@gmail.com
Phone Number: 9260971466
        """.trimIndent()

    }
}