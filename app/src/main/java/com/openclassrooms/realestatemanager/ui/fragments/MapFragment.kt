package com.openclassrooms.realestatemanager.ui.fragments

import android.os.Bundle

import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

import com.google.android.gms.maps.model.Marker

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentMapBinding
import com.openclassrooms.realestatemanager.models.PropertyWithAllData

import com.openclassrooms.realestatemanager.ui.fragments.PropertyDetailFragment.Companion.isFromDetailsFragment

import pub.devrel.easypermissions.EasyPermissions


class MapFragment : Fragment(R.layout.fragment_map),OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private var mapBinding: FragmentMapBinding? = null
    private val binding get() = mapBinding!!
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var map: GoogleMap? = null
    private val RC_LOCATION_PERMS = 101
    private lateinit var menu: Menu


    private var propertiesList: List<PropertyWithAllData> = ArrayList()

    companion object {
        var isFromMapFragment: Boolean = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapBinding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

//    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
//        showSnackBarMessage(getString(R.string.))
//    }
//
//    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
//        if (requestCode == RC_LOCATION_PERMS) displayUserLocation()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_view)

        if (isFromDetailsFragment) {
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            bottomNavigationView.visibility = View.GONE
        } else {
            requireActivity().title = getString(R.string.app_name)
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            bottomNavigationView.visibility = View.VISIBLE
        }

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }

    override fun onInfoWindowClick(p0: Marker) {
        TODO("Not yet implemented")
    }
}

