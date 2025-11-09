// MainActivity.kt
package com.skooldio.android.designlayout.workshop1

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookList: ArrayList<Book>
    private lateinit var deliveryButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize FavoriteManager
        FavoriteManager.init(this)
        CartManager.init(this)

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

        // ปุ่ม Bookmark - ไปหน้า Favorite
        val bookmarkButton: ImageButton = findViewById(R.id.button_bookmark)
        bookmarkButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        deliveryButton = findViewById(R.id.button_delivery)
        deliveryButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // --- ส่วนของ RecyclerView ---
        recyclerView = findViewById(R.id.recycler_view_books)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager

        // สร้างข้อมูลหนังสือ
        bookList = ArrayList()
        bookList.add(Book("DandAnDan เล่ม 13", "51$", R.drawable.dandan_dan))
        bookList.add(Book("Gonlin slayer เล่ม 2", "45$", R.drawable.gonlin_slayer))
        bookList.add(Book("Elden ring เล่ม 1", "45$", R.drawable.elden_ring))
        bookList.add(Book("Horimiya", "30$", R.drawable.horimiya))
        bookList.add(Book("Think comic", "110$", R.drawable.think_comic))
        bookList.add(Book("OnePiece", "40$", R.drawable.onepiece))

        bookAdapter = BookAdapter(
            bookList.toMutableList(),
            onBookClick = { book ->
                showBookDetailDialog(book)
            },
            onFavoriteClick = { book ->
                val isFavorite = FavoriteManager.toggleFavorite(book.title)
                val message = if (isFavorite) {
                    "เพิ่ม ${book.title} ใน Favorite"
                } else {
                    "ลบ ${book.title} จาก Favorite"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.adapter = bookAdapter
    }
    private fun showBookDetailDialog(book: Book) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_book_detail, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // เชื่อมต่อ Views
        val bookImage = dialogView.findViewById<ImageView>(R.id.image_book_detail)
        val bookTitle = dialogView.findViewById<TextView>(R.id.text_book_title_detail)
        val bookPrice = dialogView.findViewById<TextView>(R.id.text_book_price_detail)
        val favoriteButton = dialogView.findViewById<ImageButton>(R.id.button_favorite_dialog)
        val addToCartButton = dialogView.findViewById<android.widget.Button>(R.id.button_add_to_cart)
        val closeButton = dialogView.findViewById<ImageButton>(R.id.button_close)

        // ตั้งค่าข้อมูล
        bookImage.setImageResource(book.imageUrl)
        bookTitle.text = book.title
        bookPrice.text = book.price

        // ตั้งค่าข้อมูล
        bookImage.setImageResource(book.imageUrl)
        bookTitle.text = book.title
        bookPrice.text = book.price

        // อัปเดตไอคอน Favorite
        updateFavoriteIcon(favoriteButton, book.title)

        // ปุ่ม Favorite
        favoriteButton.setOnClickListener {
            FavoriteManager.toggleFavorite(book.title)
            updateFavoriteIcon(favoriteButton, book.title)
            bookAdapter.notifyDataSetChanged()
        }

        // ปุ่มเพิ่มเข้าตะกร้า
        addToCartButton.setOnClickListener {
            CartManager.addToCart(book.title)
            Toast.makeText(this, "เพิ่ม ${book.title} เข้าตะกร้าแล้ว", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // ปุ่มปิด
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateFavoriteIcon(button: ImageButton, bookTitle: String) {
        if (FavoriteManager.isFavorite(bookTitle)) {
            button.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            button.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    override fun onResume() {
        super.onResume()
        bookAdapter.notifyDataSetChanged()
    }
}