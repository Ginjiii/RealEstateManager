package com.openclassrooms.realestatemanager.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager

import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.openclassrooms.realestatemanager.R

import com.openclassrooms.realestatemanager.adapters.PropertyListAdapter
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyListBinding

import com.openclassrooms.realestatemanager.models.PropertyWithAllData


class PropertyListFragment : Fragment(R.layout.fragment_property_list) {

    private var fragmentPropertyListBinding: FragmentPropertyListBinding? = null
    private val binding get() = fragmentPropertyListBinding!!


    private lateinit var propertyListAdapter: PropertyListAdapter

    private var isDoubleScreenMode = false

    private lateinit var menu: Menu


    companion object {
        var isDollar: Boolean? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentPropertyListBinding = FragmentPropertyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        requireActivity().title = getString(R.string.app_name)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_view)

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        bottomNavigationView.visibility = View.VISIBLE

        configureRecyclerView()
    }

    // Setup recyclerview
    private fun configureRecyclerView() {
        propertyListAdapter = PropertyListAdapter(listOf<PropertyWithAllData>(), Glide.with(this), isDoubleScreenMode)
        binding.recyclerViewList.adapter = propertyListAdapter
        binding.recyclerViewList.layoutManager = LinearLayoutManager(activity)
    }


    // Setup toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar, menu)
        menu.getItem(0).isVisible = true
        menu.getItem(1).isVisible = false
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tb_menu_currency -> {
                when (isDollar) {
                    true -> {
                        propertyListAdapter.notifyDataSetChanged()
                        isDollar = false
                        menu.getItem(0).setIcon(R.drawable.euro)
                    }

                    null, false -> {
                        propertyListAdapter.notifyDataSetChanged()
                        isDollar = true
                        menu.getItem(0).setIcon(R.drawable.dollar)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentPropertyListBinding = null
    }
}