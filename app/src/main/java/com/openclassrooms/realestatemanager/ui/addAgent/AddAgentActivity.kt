package com.openclassrooms.realestatemanager.ui.addAgent

import android.os.Bundle
import android.view.Menu
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding

class AddAgentActivity: AppCompatActivity() {

    private var addAgentFragment: AddAgentFragment? = null
    private lateinit var binding: ActivityMainBinding
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        configureToolbar()
        configureAndShowView()
    }

//    private fun configureToolbar() {
//        setSupportActionBar(toolbar)
//        val actionBar = supportActionBar
//        actionBar?.setHomeAsUpIndicator(R.drawable.close)
//        actionBar?.setDisplayHomeAsUpEnabled(true)
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_agent_check_menu, menu)
        return true
    }

    private fun configureAndShowView(){
        addAgentFragment = supportFragmentManager.findFragmentById(R.id.main_activity_container_view) as AddAgentFragment?
        if(addAgentFragment == null){
            addAgentFragment = AddAgentFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_activity_container_view, addAgentFragment!!)
                .commit()
        }
    }
}