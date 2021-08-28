package com.openclassrooms.realestatemanager.ui
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.ListFragment
import androidx.fragment.app.commit
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.databinding.FragmentMapBinding
import com.openclassrooms.realestatemanager.ui.fragments.*
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil


class MainActivity : AppCompatActivity() {

    private var mIsDualPane = false
    private var menuToolbar: Menu? = null
    private var binding: ActivityMainBinding? = null
    private val mainBinding get() = binding!!

    private var detailsView: PropertyDetailFragment? = null
    private lateinit var rfabHelper: RapidFloatingActionHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentDetailFragment = binding.rightScreen
        mIsDualPane = fragmentDetailFragment?.visibility == View.VISIBLE

        configureScreenMode()
        setupBottomNavigation(binding)
//        configureRapidFloatingActionButton()
    }

    // Setup Bottom Navigation
    private fun setupBottomNavigation(binding: ActivityMainBinding) {
        binding.bottomNavView?.setOnItemReselectedListener { /* */ }

        binding.bottomNavView?.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.listFragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.main_activity_container_view, PropertyListFragment())
                    }
                }
                R.id.mapFragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.main_activity_container_view, MapFragment())
                    }
                }
                R.id.searchFragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.main_activity_container_view, SearchFragment())
                    }
                }
                R.id.loanFragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.main_activity_container_view, LoanFragment())
                    }
                }
            }
            true
        }
    }

    //------CONFIGURE UI------

    private fun configureScreenMode(){
        mIsDualPane = findViewById<FrameLayout>(R.id.right_screen) != null
    }

    //------Toolbar---------

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(mIsDualPane){
            menuInflater.inflate(R.menu.toolbar_tablet, menu)
        } else{
            menuInflater.inflate(R.menu.toolbar, menu)
        }
        menuToolbar = menu
        return true
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

    //------Floating button---------

//    private fun configureRapidFloatingActionButton() {
//        val rfaContent = RapidFloatingActionContentLabelList(applicationContext)
//        rfaContent.setOnRapidFloatingActionContentLabelListListener(this)
//        val items = mutableListOf<RFACLabelItem<Int>>()
//        items.add(
//            RFACLabelItem<Int>()
//            .setLabel(getString(R.string.add_new_property))
//            .setResId(R.drawable.home)
//            .setIconNormalColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))
//            .setIconPressedColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))
//            .setWrapper(0)
//        )
//        items.add(
//            RFACLabelItem<Int>()
//
//            .setLabel(getString(R.string.add_agent_menu))
//            .setResId(R.drawable.username)
//            .setIconNormalColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))
//            .setIconPressedColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))
//            .setWrapper(1)
//        )
//        rfaContent
//            .setItems(items as List<RFACLabelItem<Any>>?)
//            .setIconShadowRadius(RFABTextUtil.dip2px(applicationContext, 3F))
//            .setIconShadowColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
//            .setIconShadowDy(RFABTextUtil.dip2px(applicationContext, 3F))
//        rfabHelper = RapidFloatingActionHelper(
//            applicationContext,
//            binding?.activityMainRfal,
//            binding?.activityMainRfab,
//            rfaContent
//        ).build()
//    }
//
//    override fun onRFACItemIconClick(position: Int, item: RFACLabelItem<RFACLabelItem<Int>>?) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onRFACItemLabelClick(position: Int, item: RFACLabelItem<RFACLabelItem<Int>>?) {
//        TODO("Not yet implemented")
//    }

    //------2 views mode---------
    private fun showDetailsView(){
        detailsView = PropertyDetailFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.right_screen, detailsView!!)
            .commit()
    }

    fun openDetailsProperty(){

        if(mIsDualPane){
            showDetailsView()
        } else{
            val intent = Intent(this, PropertyDetailFragment::class.java)
        }
    }
}