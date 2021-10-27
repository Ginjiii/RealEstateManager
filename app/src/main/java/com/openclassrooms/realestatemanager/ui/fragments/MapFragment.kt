package com.openclassrooms.realestatemanager.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentMapBinding
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.ui.fragments.PropertyDetailFragment.Companion.isForDetailsFragment
import com.openclassrooms.realestatemanager.ui.fragments.PropertyDetailFragment.Companion.isFromDetailsFragment
import com.openclassrooms.realestatemanager.utils.Utils
import pub.devrel.easypermissions.EasyPermissions


class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback,
    GoogleMap.OnInfoWindowClickListener, EasyPermissions.PermissionCallbacks {

    private var mapBinding: FragmentMapBinding? = null
    private val binding get() = mapBinding!!
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var map: GoogleMap? = null
    private val RC_LOCATION_PERMS = 101
    private lateinit var menu: Menu


    private var propertiesList: List<Property> = ArrayList()

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(context, ">Allow GPS permission to use the map feature", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

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


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map?.apply {
            if (context?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED && context?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            isMyLocationEnabled = true
            uiSettings.isMyLocationButtonEnabled = false
        }
        binding.ivFindLocation.setOnClickListener {
            if (Utils.isGPSEnabled(requireContext())) {
                binding.ivFindLocation.setImageResource(R.drawable.gps)
                getDeviceLocation()
            } else {
                binding.ivFindLocation.setImageResource(R.drawable.ic_baseline_gps_off_24)
                Utils.setupAlertDialogToActivateGPS(
                    requireContext(),
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                    null
                )
            }
        }

            Utils.setupAlertDialogToActivateInternet(
                requireContext(),
                Intent(Settings.ACTION_WIFI_SETTINGS),
                Intent(Settings.ACTION_DATA_ROAMING_SETTINGS),
                null
            )

        if (!isFromDetailsFragment) {
            map?.setOnInfoWindowClickListener(this)
        }
    }

    override fun onInfoWindowClick(p0: Marker) {
        isFromMapFragment = true
        isForDetailsFragment = true
        parentFragmentManager.commit {
            if (resources.getBoolean(R.bool.isTablet)) {
                replace(R.id.main_activity_container_view, PropertyDetailFragment(), "detailsFragmentTablet")
            } else {
                replace(R.id.main_activity_container_view, PropertyDetailFragment(), "detailsFragment")
            }
            addToBackStack(null)
        }
    }

    // Find device location
    @SuppressLint("MissingPermission")
    fun getDeviceLocation() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val currentLatLng = LatLng(latitude, longitude)
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 8F)
                    map?.animateCamera(cameraUpdate)
                }
            }
        } catch (error: SecurityException) {}
    }

    // LIFE STATE MAP

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }
}

