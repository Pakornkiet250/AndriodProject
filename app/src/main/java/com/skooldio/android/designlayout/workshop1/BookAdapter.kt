package com.skooldio.android.designlayout.workshop1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(private val bookList: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    // 1. คลาสสำหรับจับคู่ View กับตัวแปร
    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCover: ImageView = itemView.findViewById(R.id.image_book_cover)
        val bookTitle: TextView = itemView.findViewById(R.id.text_book_title)
        val bookPrice: TextView = itemView.findViewById(R.id.text_book_price)
    }

    // 2. สร้าง View (โดยใช้ list_item_book.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_book, parent, false)
        return BookViewHolder(view)
    }

    // 3. ส่งข้อมูล (Book) ไปใส่ใน View
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bookTitle.text = book.title
        holder.bookPrice.text = book.price
        holder.bookCover.setImageResource(book.imageUrl)
    }

    // 4. บอก RecyclerView ว่ามีข้อมูลกี่ชิ้น
    override fun getItemCount() = bookList.size
}