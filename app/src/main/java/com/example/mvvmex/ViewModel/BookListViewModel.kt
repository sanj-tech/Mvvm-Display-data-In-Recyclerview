package com.example.mvvmex.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmex.model.Book

class BookListViewModel:ViewModel() {
    val books = MutableLiveData<List<Book>>()

    fun loadBooks() {
        // Make a network request to fetch the list of books
        // Once the response is received, update the books list
        books.value = listOf(
            Book("The Alchemist", "Paulo Coelho", "A magical story about following your dreams"),
            Book("To Kill a Mockingbird", "Harper Lee", "A powerful novel about racism and injustice")
        )
    }
}
