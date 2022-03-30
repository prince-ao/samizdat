package com.example.samizdat.homefragment

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.samizdat.MainActivity
import com.example.samizdat.R
import com.example.samizdat.databinding.FragmentBookViewBinding
import com.example.samizdat.homefragment.retrofit.HomeModelItem

class BookView : Fragment() {
    private var _binding: FragmentBookViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var downloadManager: DownloadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle: Bundle? = arguments
        val model = bundle?.getParcelable<HomeModelItem>("obj")
        Log.d("LetsGo", model.toString())
        _binding = FragmentBookViewBinding.inflate(inflater, container, false)
        binding.bookTitle.text = model?.title
        binding.bookImage.setImageBitmap(model?.imageBitmap)
        binding.bookAuthor.text = model?.author
        binding.bookDescription.text = model?.description
        binding.bookFormat.text = model?.format
        binding.bookLanguage.text = model?.language
        binding.bookPages.text = model?.pages
        binding.bookPublisher.text = model?.publisher
        binding.bookSize.text = model?.size
        binding.bookYear.text = model?.year
        binding.bookDescription.movementMethod = ScrollingMovementMethod()
        binding.bookAuthor.movementMethod = ScrollingMovementMethod()
        binding.bookPublisher.movementMethod = ScrollingMovementMethod()
        binding.backBtn.setOnClickListener {
            val mFrag = RecentViewFragment(context as MainActivity)
            val mainActivity = activity as MainActivity
            mainActivity.popStack()
        }
        binding.getButton.setOnClickListener {
            downloadManager = context?.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val uri: Uri = Uri.parse(model?.link)
            context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.mkdirs()
            val request: DownloadManager.Request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, model?.link)
            val reference: Long = downloadManager.enqueue(request)
            Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

}