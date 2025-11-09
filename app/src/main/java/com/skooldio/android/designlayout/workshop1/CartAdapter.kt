package com.skooldio.android.designlayout.workshop1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private var cartItems: MutableList<Pair<Book, Int>>,
    private val onRemoveClick: (Book) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCover: ImageView = itemView.findViewById(R.id.image_cart_book_cover)
        val bookTitle: TextView = itemView.findViewById(R.id.text_cart_book_title)
        val bookPrice: TextView = itemView.findViewById(R.id.text_cart_book_price)
        val quantity: TextView = itemView.findViewById(R.id.text_cart_quantity)
        val removeButton: ImageButton = itemView.findViewById(R.id.button_remove_from_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val (book, qty) = cartItems[position]

        holder.bookCover.setImageResource(book.imageUrl)
        holder.bookTitle.text = book.title
        holder.bookPrice.text = book.price
        holder.quantity.text = "จำนวน: $qty"

        holder.removeButton.setOnClickListener {
            onRemoveClick(book)
        }
    }

    override fun getItemCount() = cartItems.size
}