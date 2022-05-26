package com.example.samizdat.searchfragment

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.samizdat.MainActivity
import com.example.samizdat.databinding.FragmentSearchBinding
import okhttp3.RequestBody
import org.json.JSONObject

/**
 * Future Ideas
 * ---------------
 * Add infinite scroll
 **/

class SearchFragment(context: MainActivity) : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    val contxt: MainActivity

    init {
        contxt = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.searchLayout.setOnClickListener{
            binding.SearchBar.clearFocus()
            val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.SearchBar.windowToken, 0)
        }
        binding.SearchBtn.setOnClickListener {
            binding.SearchBar.clearFocus()
            val post: Post = Post(binding.SearchBar.text.toString())
            binding.SearchBar.text.clear()
            val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.SearchBar.windowToken, 0)
            if(post.getWord().isNotEmpty()){
                val mfrag: Fragment = SearchResultFragment(post, contxt)
                contxt.switchContent(mfrag)
            }
        }

        return binding.root
    }
}