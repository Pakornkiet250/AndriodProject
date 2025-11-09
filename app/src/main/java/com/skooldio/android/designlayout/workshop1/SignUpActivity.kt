package com.skooldio.android.designlayout.workshop1
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText // เพิ่ม: สำหรับดึงค่าจากช่องกรอก
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

            // --- NEW: ดึงค่า Display Name และส่งข้อมูล ---
            val nicknameEditText: EditText = findViewById(R.id.edit_text_nickname)
            val nickname = nicknameEditText.text.toString()
            // ---------------------------------------------

            // สร้าง Intent เพื่อไปหน้า MainActivity (หน้า Home)
            val intent = Intent(this, MainActivity::class.java)

            // NEW: ส่งข้อมูล Display Name ไปยัง MainActivity
            intent.putExtra("EXTRA_NICKNAME", nickname)

            // (สำคัญ) ล้างหน้าจอก่อนหน้าทั้งหมด (Login, Sign Up) ออกจาก Stack
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}