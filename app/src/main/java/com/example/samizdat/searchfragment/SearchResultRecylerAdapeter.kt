package com.example.samizdat.searchfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.samizdat.MainActivity
import com.example.samizdat.R
import com.example.samizdat.global.BookView
import com.example.samizdat.retrofit.models.SearchResult

class SearchResultRecylerAdapeter(items: SearchResult, context: Context): RecyclerView.Adapter<SearchResultRecylerAdapeter.ViewHolder>() {
    val it: SearchResult
    val contex: Context
    val inflater: LayoutInflater

    init {
        it = items
        contex = context
        inflater = LayoutInflater.from(contex)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val bookTitle: TextView
        val bookImage: ImageView
        val bookContainer: ConstraintLayout
        init {
            bookTitle = view.findViewById(R.id.bookTitle)
            bookImage = view.findViewById(R.id.bookImage)
            bookContainer = view.findViewById(R.id.bookContainer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.book_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bookTitle.text = it.array?.get(position)?.title
        holder.bookContainer.setOnClickListener { v ->
            val mFragment = BookView()
            val mBundle = Bundle()
            mBundle.putParcelable("obj", it.array?.get(position))
            mFragment.arguments = mBundle
            val mainActivity = contex as MainActivity
            mainActivity.switchContent(mFragment)
        }
        if(it.array?.get(position)?.imageBitmap == null){
            holder.bookImage.setImageResource(R.drawable.blank)
        }else {
            holder.bookImage.setImageBitmap(it.array[position].imageBitmap)
        }
    }

    override fun getItemCount(): Int = it.array!!.size

}