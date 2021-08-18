package com.openclassrooms.realestatemanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyDetailBinding

class PropertyDetailFragment : Fragment(R.layout.fragment_property_detail) {

    private var fragmentDetailBinding: FragmentPropertyDetailBinding? = null
    private val  binding get() = fragmentDetailBinding !!

    companion object {
        var isForDetailsFragment = false
        var isFromDetailsFragment = false
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        fragmentDetailBinding = FragmentPropertyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}