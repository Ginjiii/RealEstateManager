package com.openclassrooms.realestatemanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyListBinding

class PropertyListFragment : Fragment(R.layout.fragment_property_list) {

    private var fragmentPropertyListBinding: FragmentPropertyListBinding? = null
    private val binding get() = fragmentPropertyListBinding!!

    companion object {
        var isDollar: Boolean? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentPropertyListBinding = FragmentPropertyListBinding.inflate(inflater, container, false)
        return binding.root
    }
}