package com.openclassrooms.realestatemanager.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.PropertyListAdapter
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyListBinding
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.ui.MainActivity
import com.openclassrooms.realestatemanager.utils.Currency


class PropertyListFragment : Fragment(R.layout.fragment_property_list) {

    private var fragmentPropertyListBinding: FragmentPropertyListBinding? = null
    private val binding get() = fragmentPropertyListBinding!!

    protected var currentCurrency: Currency? = null

    private lateinit var propertyListAdapter: PropertyListAdapter

    private var isDoubleScreenMode = false

    private lateinit var menu: Menu
    private lateinit var propertiesList: List<Property>


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

        checkScreenMode()
        return binding.root
    }

    override fun onResume(){
        super.onResume()
        if (!isDoubleScreenMode) propertyListAdapter?.updateSelection(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        updateDummyList()
        configureRecyclerView()
    }
    // Setup recyclerview
    private fun configureRecyclerView() {
        propertyListAdapter = PropertyListAdapter(
            propertiesList,
            currentCurrency,
            Glide.with(this),
            isDoubleScreenMode
        )

        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewList.adapter = propertyListAdapter
    }

//    private fun updateDummyList(){
//        val agent = Agent( "BBB", "CCC","eee@rem.com","060000000" )
//        val property = Property("0",type = TypeProperty.HOUSE, 0,0,0,0,0,"belle maison",
//            "address", "91919191", "city", "FRANCE", true, "available date", "soldDate", 0, "photo", "photoLabel" )
//        val propertyWithAllData = PropertyWithAllData(property, agent)
//
//        propertiesList = listOf<PropertyWithAllData>(propertyWithAllData)
//        Log.d("TAGii", "size:" + propertiesList.size)
//        propertyListAdapter.update(propertyWithAllDataList)
//    }


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

    private fun checkScreenMode(){
        if(activity is MainActivity){
            isDoubleScreenMode = (activity as MainActivity).isDoubleScreenMode
        }
    }
}