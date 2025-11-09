package com.skooldio.android.designlayout.workshop1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(
    private var bookList: List<Book>,
    private val onBookClick: (Book) -> Unit,
    private val onFavoriteClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCover: ImageView = itemView.findViewById(R.id.image_book_cover)
        val bookTitle: TextView = itemView.findViewById(R.id.text_book_title)
        val bookPrice: TextView = itemView.findViewById(R.id.text_book_price)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.button_favorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bookTitle.text = book.title
        holder.bookPrice.text = book.price
        holder.bookCover.setImageResource(book.imageUrl)

        updateFavoriteIcon(holder.favoriteButton, book.title)

        holder.itemView.setOnClickListener {
            onBookClick(book)
        }

        holder.favoriteButton.setOnClickListener {
            onFavoriteClick(book)
            updateFavoriteIcon(holder.favoriteButton, book.title)
        }
    }

    private fun updateFavoriteIcon(button: ImageButton, bookTitle: String) {
        if (FavoriteManager.isFavorite(bookTitle)) {
            button.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            button.setImageResource(R.drawable.ic_favorite_border)
        }
    }


    // 4. บอก RecyclerView ว่ามีข้อมูลกี่ชิ้น
    override fun getItemCount() = bookList.size

    fun updateList(newList: List<Book>) {
        bookList = newList
        notifyDataSetChanged()
    }
}