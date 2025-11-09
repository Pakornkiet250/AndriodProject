package com.skooldio.android.designlayout.workshop1

import android.content.Context
import android.content.SharedPreferences

object CartManager {
    private const val PREF_NAME = "CartBooks"
    private const val KEY_CART = "cart_items"

    private lateinit var sharedPreferences: SharedPreferences
    private val cartItems = mutableMapOf<String, Int>() // title -> quantity

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        loadCart()
    }

    private fun loadCart() {
        val savedCart = sharedPreferences.getStringSet(KEY_CART, emptySet())
        cartItems.clear()
        savedCart?.forEach { item ->
            val parts = item.split("|")
            if (parts.size == 2) {
                cartItems[parts[0]] = parts[1].toIntOrNull() ?: 1
            }
        }
    }

    private fun saveCart() {
        val cartSet = cartItems.map { "${it.key}|${it.value}" }.toSet()
        sharedPreferences.edit().putStringSet(KEY_CART, cartSet).apply()
    }

    fun addToCart(bookTitle: String, quantity: Int = 1) {
        val currentQty = cartItems[bookTitle] ?: 0
        cartItems[bookTitle] = currentQty + quantity
        saveCart()
    }

    fun removeFromCart(bookTitle: String) {
        cartItems.remove(bookTitle)
        saveCart()
    }

    fun getQuantity(bookTitle: String): Int {
        return cartItems[bookTitle] ?: 0
    }

    fun getCartItems(allBooks: List<Book>): List<Pair<Book, Int>> {
        return allBooks.mapNotNull { book ->
            val qty = cartItems[book.title]
            if (qty != null && qty > 0) {
                Pair(book, qty)
            } else null
        }
    }

    fun getTotalItems(): Int {
        return cartItems.values.sum()
    }

    fun getTotalPrice(allBooks: List<Book>): Int {
        return getCartItems(allBooks).sumOf { (book, qty) ->
            val price = book.price.replace("$", "").toIntOrNull() ?: 0
            price * qty
        }
    }

    fun clearCart() {
        cartItems.clear()
        saveCart()
    }
}