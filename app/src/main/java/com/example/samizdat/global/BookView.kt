package com.example.samizdat.global

import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.samizdat.MainActivity
import com.example.samizdat.R
import com.example.samizdat.database.FavBook
import com.example.samizdat.databinding.FragmentBookViewBinding
import com.example.samizdat.homefragment.RecentViewFragment
import com.example.samizdat.retrofit.models.BookInfo
import java.io.ByteArrayOutputStream
import java.io.File


class BookView : Fragment() {
    val viewModel: FavBookViewModel by viewModels()
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
        _binding = FragmentBookViewBinding.inflate(inflater, container, false)
        val bundle: Bundle? = arguments
        val model = bundle?.getParcelable<BookInfo>("obj")
        Log.d("Title: ", model?.title.toString())
        viewModel.findBook(model?.title.toString())
        viewModel.getSearchResults().observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                binding.star.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.star_yellow))
            }
        })
        binding.star.setOnClickListener {
            if(binding.star.drawable.constantState?.equals(AppCompatResources.getDrawable(requireContext(), R.drawable.star)?.constantState) == true){
                var ff: FavBook? = null;
                if(model != null) {

                    ff = FavBook(model.title!!, model.image!!, model.link!!, model.publisher!!, model.year!!, model.language!!, model.size!!, model.description!!, model.isbm!!, model.id!!, model.author!!, model.format!!, model.pages!!)
                }
                val f: FavBook? = ff
                viewModel.insertBook(f!!)
                binding.star.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.star_yellow))
            }else{
                viewModel.deleteBook(model!!)
                binding.star.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.star))
            }
        }
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
            val realTitle = model?.title.toString().replace(' ', '_')
            downloadManager = context?.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            Log.d("link", model?.link.toString())
            val uri: Uri = Uri.parse(model?.link.toString())
            context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.mkdirs()
            val request: DownloadManager.Request = DownloadManager.Request(uri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                realTitle
            )
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setTitle(realTitle + model?.link.toString().substring(model?.link.toString().lastIndexOf(".")))
            Toast.makeText(context, "Download started...", Toast.LENGTH_LONG).show()
            val reference: Long = downloadManager.enqueue(request)
        }
        return binding.root
    }

}