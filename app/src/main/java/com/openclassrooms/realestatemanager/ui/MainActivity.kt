package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.ui.fragments.LoanFragment
import com.openclassrooms.realestatemanager.ui.fragments.MapFragment
import com.openclassrooms.realestatemanager.ui.fragments.PropertyListFragment
import com.openclassrooms.realestatemanager.ui.fragments.SearchFragment



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupBottomNavigation(binding)


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fl_container, PropertyListFragment())
            }
        }
    }


    // Setup Bottom Navigation
    private fun setupBottomNavigation(binding: ActivityMainBinding) {
        binding.bottomNavView.setOnItemReselectedListener { /* */ }

        binding.bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.listFragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.fl_container, PropertyListFragment())
                    }
                }
                R.id.mapFragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.fl_container, MapFragment())
                    }
                }
                R.id.searchFragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.fl_container, SearchFragment())
                    }
                }
                R.id.loanFragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.fl_container, LoanFragment())
                    }
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}