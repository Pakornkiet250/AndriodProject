package com.skooldio.android.designlayout.workshop1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var emptyView: TextView
    private lateinit var totalPriceText: TextView
    private lateinit var checkoutButton: Button
    private val allBooks = ArrayList<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // ซ่อน ActionBar เดิม
        supportActionBar?.hide()

        // คลิกชื่อแอพ - กลับหน้า Home
        val appNameText: TextView = findViewById(R.id.text_app_name)
        appNameText.setOnClickListener {
            finish()
        }

        // ปุ่ม Profile
        val profileButton: ImageButton = findViewById(R.id.button_profile)
        profileButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // ปุ่ม Bookmark
        val bookmarkButton: ImageButton = findViewById(R.id.button_bookmark)
        bookmarkButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        emptyView = findViewById(R.id.text_empty_cart)
        recyclerView = findViewById(R.id.recycler_view_cart)
        totalPriceText = findViewById(R.id.text_total_price)
        checkoutButton = findViewById(R.id.button_checkout)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // สร้างข้อมูลหนังสือทั้งหมด
        allBooks.add(Book("DandAnDan เล่ม 13", "51$", R.drawable.dandan_dan))
        allBooks.add(Book("Gonlin slayer เล่ม 2", "45$", R.drawable.gonlin_slayer))
        allBooks.add(Book("Elden ring เล่ม 1", "45$", R.drawable.elden_ring))
        allBooks.add(Book("Horimiya", "30$", R.drawable.horimiya))
        allBooks.add(Book("Think comic", "110$", R.drawable.think_comic))
        allBooks.add(Book("OnePiece", "40$", R.drawable.onepiece))

        // ปุ่ม Checkout
        checkoutButton.setOnClickListener {
            Toast.makeText(this, "กำลังดำเนินการชำระเงิน...", Toast.LENGTH_SHORT).show()
        }

        loadCart()
    }

    private fun loadCart() {
        val cartItems = CartManager.getCartItems(allBooks)

        if (cartItems.isEmpty()) {
            emptyView.visibility = TextView.VISIBLE
            recyclerView.visibility = RecyclerView.GONE
            checkoutButton.isEnabled = false
            totalPriceText.text = "ราคารวม: $0"
        } else {
            emptyView.visibility = TextView.GONE
            recyclerView.visibility = RecyclerView.VISIBLE
            checkoutButton.isEnabled = true

            cartAdapter = CartAdapter(
                cartItems.toMutableList(),
                onRemoveClick = { book ->
                    CartManager.removeFromCart(book.title)
                    Toast.makeText(this, "ลบ ${book.title} ออกจากตะกร้า", Toast.LENGTH_SHORT).show()
                    loadCart()
                }
            )
            recyclerView.adapter = cartAdapter

            val totalPrice = CartManager.getTotalPrice(allBooks)
            totalPriceText.text = "ราคารวม: $$totalPrice"
        }
    }
}