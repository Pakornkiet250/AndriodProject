// FavoriteManager.kt
package com.skooldio.android.designlayout.workshop1

import android.content.Context
import android.content.SharedPreferences

object FavoriteManager {
    private const val PREF_NAME = "FavoriteBooks"
    private const val KEY_FAVORITES = "favorites"

    private lateinit var sharedPreferences: SharedPreferences
    private val favoriteBooks = mutableSetOf<String>()

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        loadFavorites()
    }

    private fun loadFavorites() {
        val savedFavorites = sharedPreferences.getStringSet(KEY_FAVORITES, emptySet())
        favoriteBooks.clear()
        favoriteBooks.addAll(savedFavorites ?: emptySet())
    }

    private fun saveFavorites() {
        sharedPreferences.edit().putStringSet(KEY_FAVORITES, favoriteBooks).apply()
    }

    fun isFavorite(bookTitle: String): Boolean {
        return favoriteBooks.contains(bookTitle)
    }

    fun toggleFavorite(bookTitle: String): Boolean {
        val isFav = if (favoriteBooks.contains(bookTitle)) {
            favoriteBooks.remove(bookTitle)
            false
        } else {
            favoriteBooks.add(bookTitle)
            true
        }
        saveFavorites()
        return isFav
    }

    fun getFavoriteBooks(allBooks: List<Book>): List<Book> {
        return allBooks.filter { favoriteBooks.contains(it.title) }
    }

    fun clearAll() {
        favoriteBooks.clear()
        saveFavorites()
    }
}