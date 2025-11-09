package com.skooldio.android.designlayout.workshop1
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
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
        // *** สำคัญ: เปลี่ยน R.drawable.ชื่อไฟล์รูปภาพของคุณ ให้ตรงกับที่คุณวางในโฟลเดอร์ drawable ***
        bookList.add(Book("DandAnDan เล่ม 13", "51$", R.drawable.dandadan)) // ตัวอย่าง: dandan_dan.jpg -> R.drawable.dandadan
        bookList.add(Book("Gonlin slayer เล่ม 2", "45$", R.drawable.gonlin_slayer))
        bookList.add(Book("Elden ring เล่ม 1", "45$", R.drawable.elden_ring))
        bookList.add(Book("Horimiya", "30$", R.drawable.horimiya))
        bookList.add(Book("Think comic", "110$", R.drawable.think_comic))
        bookList.add(Book("Wolverine", "65$", R.drawable.wolverine))
        bookList.add(Book("Superman 1", "70$", R.drawable.superman))
        bookList.add(Book("Comic draw", "250$", R.drawable.comic_draw))
        bookList.add(Book("OnePiece", "40$", R.drawable.onepiece))

        bookAdapter = BookAdapter(bookList)
        recyclerView.adapter = bookAdapter
    }
}