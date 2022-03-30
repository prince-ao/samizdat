package com.example.samizdat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.samizdat.bookfragment.BooksFragment
import com.example.samizdat.databinding.ActivityMainBinding
import com.example.samizdat.favfragment.FavFragment
import com.example.samizdat.homefragment.RecentViewFragment
import com.example.samizdat.searchfragment.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomnavigationbar.background = null
        supportFragmentManager.beginTransaction().replace(R.id.framecontainer, RecentViewFragment(this)).commit()

        binding.bottomnavigationbar.setOnItemSelectedListener {
            var temp: Fragment? = null
            when(it.itemId){
                R.id.nHome -> temp = RecentViewFragment(this)
                R.id.nSearch -> temp = SearchFragment()
                R.id.nFavorites -> temp = FavFragment()
                R.id.nBooks -> temp = BooksFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.framecontainer, temp!!).commit()

            true
        }


    }

    fun switchContent(fragment: Fragment) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.framecontainer, fragment).addToBackStack(null).commit()
    }

    fun popStack(){
        supportFragmentManager.popBackStack()
    }

}