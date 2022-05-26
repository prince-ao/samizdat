package com.example.samizdat.searchfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samizdat.MainActivity
import com.example.samizdat.databinding.FragmentSearchResultBinding
import com.example.samizdat.homefragment.RecentViewFragment
import com.example.samizdat.retrofit.models.SearchResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class SearchResultFragment(private val word: Post, private val contex: MainActivity) : Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private var adapter: RecyclerView.Adapter<SearchResultRecylerAdapeter.ViewHolder>? = null
    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var viewModel: SearchViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.backBtn.setOnClickListener {
            contex.popStack()
        }
        if(viewModel.results.value == null) {
            binding.progressBar2.visibility = View.VISIBLE
            viewModel.search(this.word)
        }
        viewModel.results.observe(viewLifecycleOwner) { items: SearchResult? ->
            if(items?.suggestion?.isNotEmpty() == true){
                binding.suggestion.text = items.suggestion
                binding.suggestion.visibility = View.VISIBLE
                binding.textView8.visibility = View.VISIBLE
                binding.suggestion.setOnClickListener {
                    binding.imageView2.visibility = View.INVISIBLE
                    binding.suggestion.visibility = View.INVISIBLE
                    binding.textView8.visibility = View.INVISIBLE
                    binding.progressBar2.visibility = View.VISIBLE
                    viewModel.search(Post(binding.suggestion.text.toString()))
                }
            }
            if(items?.array?.isNotEmpty() == true) {
                if(items.array[0].imageBitmap == null) {
                    binding.progressBar2.visibility = View.VISIBLE
                    myCoroutineScope.launch {
                        doStuff(items)
                        adapter!!.notifyDataSetChanged()
                    }
                }
                adapter = view?.context?.let {
                    SearchResultRecylerAdapeter(
                        context = contex,
                        items = items
                    )
                }!!
                val layoutManager = GridLayoutManager(this.context, 2)
                binding.recyclerView2.layoutManager = layoutManager
                binding.recyclerView2.adapter = adapter
            }else{
                binding.progressBar2.visibility = View.INVISIBLE
                binding.imageView2.visibility = View.VISIBLE
            }
        }
        return binding.root
    }

    private suspend fun doStuff(items: SearchResult) {
        items.array?.map {
            it.imageBitmap = it.image.let { it1 -> RecentViewFragment(null).bitMapper(it1!!)  }
        }
        binding.progressBar2.visibility = View.INVISIBLE
    }
}