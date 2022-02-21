package com.example.samizdat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MenuItem
import com.example.samizdat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomnavigationbar.background = null
        supportFragmentManager.beginTransaction().replace(R.id.framecontainer, HomeFragment()).commit()

        binding.bottomnavigationbar.setOnItemSelectedListener {
            var temp: Fragment? = null
            when(it.itemId){
                R.id.nHome -> temp = HomeFragment()
                R.id.nSearch -> temp = SearchFragment()
                R.id.nFavorites -> temp = FavFragment()
                R.id.nBooks -> temp = BooksFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.framecontainer, temp!!).commit()

            true
        }
    }
}