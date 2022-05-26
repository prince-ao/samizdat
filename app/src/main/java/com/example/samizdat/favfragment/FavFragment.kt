package com.example.samizdat.favfragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samizdat.MainActivity
import com.example.samizdat.databinding.FragmentFavBinding
import com.example.samizdat.global.FavBookViewModel
import com.example.samizdat.retrofit.models.BookInfo
import com.example.samizdat.homefragment.RecentViewFragment
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.*

class FavFragment(private val contex: MainActivity?) : Fragment() {
    val viewModel: FavBookViewModel by viewModels()
    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!
    private var adapter: RecyclerView.Adapter<FavRecyclerAdapter.ViewHolder>? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var layoutManager: RecyclerView.LayoutManager? = null

    companion object {
        fun newInstance() = FavFragment(null)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        viewModel.getAllProducts()?.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                //val bookContainer: ArrayList<BookInfo> = arrayListOf()
                val aBookContainer = arrayOfNulls<BookInfo>(it.size)
                for(x in it.indices){
                    val k = BookInfo(it[x].image, it[x].link, it[x].title, it[x].publisher, it[x].year, it[x].language, it[x].pages, it[x].size, it[x].description, it[x].isbm, it[x].id, it[x].author, it[x].format, null)
                    aBookContainer[x] = k
                }
                binding.favoriteLoading.visibility = View.VISIBLE
                coroutineScope.launch {
                    doStuff(aBookContainer)
                    adapter!!.notifyDataSetChanged()
                }
                adapter = view?.context?.let { FavRecyclerAdapter(aBookContainer, contex!!) }
                layoutManager = GridLayoutManager(this.context, 2)
                binding.favoriteView.layoutManager = layoutManager
                binding.favoriteView.adapter = adapter
            }
        })
        return binding.root
    }

    suspend fun bitMapper(url: String): Bitmap? =
        coroutineScope.async(Dispatchers.Default) {
            val client = HttpClient(Android) {
                expectSuccess = false
            }
            Log.d("URL", url)
            if (url.isEmpty()) {
                return@async null
            }
            val response: HttpResponse = client.get(url)
            if(response.status.value in 399..599){
                return@async null
            }
            val byteArray = response.receive<InputStream>()
            client.close()
            return@async BitmapFactory.decodeStream(byteArray)
        }.await()

    private suspend fun doStuff(items: Array<BookInfo?>) {
        items.map {
            it?.imageBitmap = it?.image.let { it1 -> bitMapper(it1!!) }
        }
        binding.favoriteLoading.visibility = View.INVISIBLE
    }
}