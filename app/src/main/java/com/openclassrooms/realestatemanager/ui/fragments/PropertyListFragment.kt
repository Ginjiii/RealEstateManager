package com.openclassrooms.realestatemanager.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.PropertyListAdapter
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyListBinding
import com.openclassrooms.realestatemanager.models.PropertyWithAllData
import com.openclassrooms.realestatemanager.ui.viewModels.PropertyListViewModel
import com.openclassrooms.realestatemanager.utils.Currency


class PropertyListFragment : Fragment(R.layout.fragment_property_list) {

    private var fragmentPropertyListBinding: FragmentPropertyListBinding? = null
    private val binding get() = fragmentPropertyListBinding!!

    private val listViewModel: PropertyListViewModel by viewModels()
    protected var currentCurrency: Currency? = null

    private lateinit var propertyListAdapter: PropertyListAdapter

    private var isDoubleScreenMode = false

    private lateinit var menu: Menu
    private lateinit var propertiesList: List<PropertyWithAllData>


    companion object {
        var isDollar: Boolean? = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentPropertyListBinding =
            FragmentPropertyListBinding.inflate(inflater, container, false)
        configureRecyclerView()
        return binding.root
    }

    // Setup recyclerview
    private fun configureRecyclerView() {
        propertyListAdapter = PropertyListAdapter(
            listOf<PropertyWithAllData>(),
            currentCurrency,
            Glide.with(this),
            isDoubleScreenMode
        )
        binding.recyclerViewList.adapter = propertyListAdapter
        binding.recyclerViewList.layoutManager = LinearLayoutManager(activity)
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
}