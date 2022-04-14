package com.example.samizdat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.samizdat.bookfragment.BooksFragment
import com.example.samizdat.databinding.ActivityMainBinding
import com.example.samizdat.favfragment.FavFragment
import com.example.samizdat.homefragment.RecentViewFragment
import com.example.samizdat.searchfragment.SearchFragment
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentStates = arrayOfNulls<Fragment.SavedState>(4)
        val fragmentStack = Stack<Pair<Int, Fragment>>()

        binding.bottomnavigationbar.background = null
        val home = RecentViewFragment(this)
        fragmentStack.push(Pair(0, home))
        supportFragmentManager.beginTransaction().replace(R.id.framecontainer, home).commit()

        binding.bottomnavigationbar.setOnItemSelectedListener {
            var temp: Fragment? = null
            var num: Int? = null
            when(it.itemId){
                R.id.nHome -> {
                    num = 0
                    temp = RecentViewFragment(this)
                    temp.setInitialSavedState(fragmentStates[0])
                }
                R.id.nSearch -> {
                    num = 1
                    temp = SearchFragment(this)
                    temp.setInitialSavedState(fragmentStates[1])
                }
                R.id.nFavorites -> {
                    num = 2
                    temp = FavFragment()
                    temp.setInitialSavedState(fragmentStates[2])
                }
                R.id.nBooks -> {
                    num = 3
                    temp = BooksFragment()
                    temp.setInitialSavedState(fragmentStates[3])
                }
            }
            val prevFrag = fragmentStack.pop()
            fragmentStates[prevFrag.first] = supportFragmentManager.saveFragmentInstanceState(prevFrag.second)!!
            fragmentStack.push(Pair(num!!, temp!!))
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