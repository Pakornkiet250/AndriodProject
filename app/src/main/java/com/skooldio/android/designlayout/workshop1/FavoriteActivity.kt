// FavoriteActivity.kt
package com.skooldio.android.designlayout.workshop1

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var emptyView: TextView
    private val allBooks = ArrayList<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // ซ่อน ActionBar เดิม
        supportActionBar?.hide()

        // คลิกชื่อแอพ - กลับหน้า Home
        val appNameText: TextView = findViewById(R.id.text_app_name)
        appNameText.setOnClickListener {
            finish() // กลับหน้า MainActivity
        }

        // ปุ่ม Profile
        val profileButton: ImageButton = findViewById(R.id.button_profile)
        profileButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // ปุ่ม Bookmark (อยู่หน้า Favorite อยู่แล้ว)
        val bookmarkButton: ImageButton = findViewById(R.id.button_bookmark)
        bookmarkButton.setOnClickListener {
            Toast.makeText(this, "คุณอยู่ในหน้า Favorite แล้ว", Toast.LENGTH_SHORT).show()
        }

        emptyView = findViewById(R.id.text_empty_favorite)
        recyclerView = findViewById(R.id.recycler_view_favorites)

        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager

        // สร้างข้อมูลหนังสือทั้งหมด (เหมือน MainActivity)
        allBooks.add(Book("DandAnDan เล่ม 13", "51$", R.drawable.dandan_dan))
        allBooks.add(Book("Gonlin slayer เล่ม 2", "45$", R.drawable.gonlin_slayer))
        allBooks.add(Book("Elden ring เล่ม 1", "45$", R.drawable.elden_ring))
        allBooks.add(Book("Horimiya", "30$", R.drawable.horimiya))
        allBooks.add(Book("Think comic", "110$", R.drawable.think_comic))
        allBooks.add(Book("OnePiece", "40$", R.drawable.onepiece))

        loadFavorites()
    }

    private fun loadFavorites() {
        val favoriteBooks = FavoriteManager.getFavoriteBooks(allBooks)

        if (favoriteBooks.isEmpty()) {
            emptyView.visibility = TextView.VISIBLE
            recyclerView.visibility = RecyclerView.GONE
        } else {
            emptyView.visibility = TextView.GONE
            recyclerView.visibility = RecyclerView.VISIBLE

            bookAdapter = BookAdapter(
                favoriteBooks.toMutableList(),
                onBookClick = { book ->
                    Toast.makeText(this, "คลิก: ${book.title}", Toast.LENGTH_SHORT).show()
                },
                onFavoriteClick = { book ->
                    FavoriteManager.toggleFavorite(book.title)
                    Toast.makeText(this, "ลบ ${book.title} จาก Favorite", Toast.LENGTH_SHORT).show()
                    loadFavorites() // โหลดใหม่
                }
            )
            recyclerView.adapter = bookAdapter
        }
    }
}