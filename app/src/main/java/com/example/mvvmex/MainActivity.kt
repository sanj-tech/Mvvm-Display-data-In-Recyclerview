package com.example.mvvmex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmex.ViewModel.BookListViewModel
import com.example.mvvmex.adapters.BookListAdapter
import com.example.mvvmex.databinding.ActivityMainBinding
import com.example.mvvmex.model.Book

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BookListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        //viewModel = ViewModelProviders.of(this).get(BookListViewModel::class.java)
        viewModel = ViewModelProvider(this).get(BookListViewModel::class.java)

        val adapter = BookListAdapter(this, viewModel.books)
        val recyclerView = findViewById<RecyclerView>(R.id.bookRv)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.loadBooks()
        viewModel.books.observe(this, Observer<List<Book>> { books ->
            adapter.notifyDataSetChanged()
        })
    }
}