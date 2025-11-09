package com.skooldio.android.designlayout.workshop1
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // 1. ปุ่ม "login" บนแถบ (โค้ดเดิม)
        val loginText: TextView = findViewById(R.id.text_login_nav)
        loginText.setOnClickListener {
            finish()
        }

        // 2. ปุ่ม "SIGN UP" สีเขียว (เพิ่มเข้ามาใหม่)
        val signUpButton: Button = findViewById(R.id.button_sign_up)
        signUpButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}