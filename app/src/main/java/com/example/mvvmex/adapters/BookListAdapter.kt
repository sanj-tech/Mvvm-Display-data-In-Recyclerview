package com.example.mvvmex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmex.R
import com.example.mvvmex.databinding.ListItemBookBinding
import com.example.mvvmex.model.Book

class BookListAdapter(var context: Context, var bookList: LiveData<List<Book>>) :
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListAdapter.ViewHolder {

        var binding: ListItemBookBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.list_item_book,
                parent,
                false
            )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: BookListAdapter.ViewHolder, position: Int) {
        val book = bookList.value?.get(position)
        holder.binding.txtTitle.text = book!!.title
        holder.binding.txtAuthor.text = book?.author
        holder.binding.txtDes.text = book.description

    }

    override fun getItemCount(): Int {
        return bookList.value!!.size
    }

    class ViewHolder(val binding: ListItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

    }


}