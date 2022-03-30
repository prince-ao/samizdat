package com.example.samizdat.homefragment

import android.R.id
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.samizdat.MainActivity
import com.example.samizdat.R
import com.example.samizdat.homefragment.retrofit.HomeModelItem


class HomeRecyclerAdapter(items: List<HomeModelItem>, context: Context): RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
    val it: List<HomeModelItem>
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
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_books, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bookTitle.text = it[position].title
        holder.bookContainer.setOnClickListener{ v ->
            //v.performClick()
            val mFragment = BookView()
            val mBundle = Bundle()
            mBundle.putParcelable("obj", it[position])
            mFragment.arguments = mBundle
            val mainActivity = contex as MainActivity
            mainActivity.switchContent(mFragment)
        }
//        holder.bookContainer.setOnClickListener { v ->
//            val activity = v.context as AppCompatActivity
//            val fragment = BookView()
//            activity.supportFragmentManager.beginTransaction().replace(R.id.home_nav_host, fragment).addToBackStack(null).commit()
//        }
        if(it[position].imageBitmap == null){
            holder.bookImage.setImageResource(R.drawable.blank)
        }else {
            holder.bookImage.setImageBitmap(it[position].imageBitmap)
        }
    }

    override fun getItemCount(): Int = it.size
}