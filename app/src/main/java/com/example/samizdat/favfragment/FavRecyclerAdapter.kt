package com.example.samizdat.favfragment

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
import com.example.samizdat.database.FavBook
import com.example.samizdat.global.BookView
import com.example.samizdat.retrofit.models.BookInfo

class FavRecyclerAdapter(items: Array<BookInfo?>, context: Context): RecyclerView.Adapter<FavRecyclerAdapter.ViewHolder>() {

    val it: Array<BookInfo?>
    val contex: Context
    init {
        it = items
        contex = context
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
        holder.bookTitle.text = it[position]?.title
        holder.bookContainer.setOnClickListener{ v ->
            //v.performClick()
            val mFragment = BookView()
            val mBundle = Bundle()
            mBundle.putParcelable("obj", it[position])
            mFragment.arguments = mBundle
            val mainActivity = contex as MainActivity
            mainActivity.switchContent(mFragment)
        }
        if(it[position]?.imageBitmap == null){
            holder.bookImage.setImageResource(R.drawable.blank)
        }else {
            holder.bookImage.setImageBitmap(it[position]?.imageBitmap)
        }
    }

    override fun getItemCount(): Int = it.size
}