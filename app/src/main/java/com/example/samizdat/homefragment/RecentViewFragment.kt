package com.example.samizdat.homefragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samizdat.MainActivity
import com.example.samizdat.retrofit.models.BookInfo
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
import com.example.samizdat.databinding.FragmentRecentViewBinding

class RecentViewFragment(private val contex: MainActivity?) : Fragment() {


    private var _binding: FragmentRecentViewBinding? = null
    private val binding get() = _binding!!
    private var adapter: RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>? = null
    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var viewModel: HomeViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentViewBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        if(savedInstanceState?.getParcelableArray("obj") != null){
            viewModel.recent.value = savedInstanceState.getParcelableArray("obj") as Array<BookInfo>
        }else{
            viewModel.getRecent()
        }
        viewModel.recent.observe(viewLifecycleOwner) { item: Array<BookInfo>? ->
            if (item != null) {
                if(item[0].imageBitmap == null) {
                    binding.progressBar.visibility = View.VISIBLE
                    val job = myCoroutineScope.launch {
                        doStuff(item)
                        adapter!!.notifyDataSetChanged()
                    }
                }
                adapter = view?.context?.let { HomeRecyclerAdapter(item, contex!!) }!!
                val layoutManager = GridLayoutManager(this.context, 2)
                binding.recyclerView.layoutManager = layoutManager
                binding.recyclerView.adapter = adapter
            } else {
                Toast.makeText(view?.context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArray("obj", viewModel.recent.value)
        outState.clear()
    }

    suspend fun bitMapper(url: String): Bitmap? =
        myCoroutineScope.async(Dispatchers.Default) {
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

    suspend fun doStuff(items: Array<BookInfo>) {
        items.map {
            it.imageBitmap = it.image.let { it1 -> bitMapper(it1!!) }
        }
        binding.progressBar.visibility = View.INVISIBLE
    }

}