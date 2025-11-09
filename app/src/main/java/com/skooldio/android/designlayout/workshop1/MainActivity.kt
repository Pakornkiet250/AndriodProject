package com.skooldio.android.designlayout.workshop1

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookList: ArrayList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // *** NEW: รับข้อมูลและแสดงผล Welcome Message ***
        val nickname = intent.getStringExtra("EXTRA_NICKNAME")
        if (nickname != null && nickname.isNotBlank()) {
            val welcomeText: TextView = findViewById(R.id.text_welcome_message)
            welcomeText.text = "Welcome, $nickname!"
        }
        // ************************************************

        // เชื่อมต่อปุ่ม Profile
        val profileButton: ImageButton = findViewById(R.id.button_profile)
        profileButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // --- ส่วนของ RecyclerView ---
        recyclerView = findViewById(R.id.recycler_view_books)
        val layoutManager = GridLayoutManager(this, 3) // 3 คอลัมน์
        recyclerView.layoutManager = layoutManager

        // สร้างข้อมูลหนังสือ (Dummy Data) พร้อมรูปภาพจริง
        bookList = ArrayList()

        // อ้างอิงถึงไฟล์รูปภาพที่คุณได้เพิ่มเข้าไปใน res/drawable/
        bookList.add(Book("DandaDan เล่ม 13", "51$", R.drawable.dandan_dan))
        bookList.add(Book("Goblin slayer เล่ม 2", "45$", R.drawable.gonlin_slayer))
        bookList.add(Book("Elden ring เล่ม 1", "45$", R.drawable.elden_ring))
        bookList.add(Book("Horimiya", "30$", R.drawable.horimiya))
        bookList.add(Book("Think comic", "110$", R.drawable.think_comic))
        bookList.add(Book("OnePiece", "40$", R.drawable.onepiece))

        bookAdapter = BookAdapter(bookList)
        recyclerView.adapter = bookAdapter
    }
}