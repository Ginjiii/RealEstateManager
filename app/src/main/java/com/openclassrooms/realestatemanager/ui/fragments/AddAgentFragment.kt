package com.openclassrooms.realestatemanager.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentAddAgentBinding
import com.openclassrooms.realestatemanager.databinding.FragmentAddAgentBinding.*
import com.openclassrooms.realestatemanager.databinding.FragmentLoanBinding
import com.openclassrooms.realestatemanager.utils.IMAGE_ONLY_TYPE
import com.openclassrooms.realestatemanager.utils.PERMS_EXT_STORAGE
import com.openclassrooms.realestatemanager.utils.RC_CHOOSE_PHOTO
import com.openclassrooms.realestatemanager.utils.RC_IMAGE_PERMS
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class AddAgentFragment : Fragment(),EasyPermissions.PermissionCallbacks{

    private var fragmentAddAgentBinding: FragmentAddAgentBinding? = null
    private val binding get() = fragmentAddAgentBinding!!

    private var uriProfileImage: String? = null
    private var urlInDevice: String? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentAddAgentBinding = FragmentAddAgentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_CHOOSE_PHOTO){
            if(resultCode == Activity.RESULT_OK){
                data?.let{
                    savePicturePicked(it)
                }
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        showSnackBarMessage(getString(R.string.allow_storage))
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if(requestCode == RC_IMAGE_PERMS) chooseProfilePictureFromPhone()
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    fun requestPermissionStorage(fragment: Fragment): Boolean {
        if (!EasyPermissions.hasPermissions(fragment.requireActivity(), PERMS_EXT_STORAGE)) {
            EasyPermissions.requestPermissions(
                fragment, fragment.requireActivity().getString(R.string.storage_perm_request), RC_IMAGE_PERMS, PERMS_EXT_STORAGE
            )
            return (EasyPermissions.hasPermissions(fragment.requireActivity(), PERMS_EXT_STORAGE))
        }

        return true
    }

    fun intentSinglePicture(): Intent{
        return Intent().apply {
            action = Intent.ACTION_PICK
            type = IMAGE_ONLY_TYPE
        }
    }

    private fun chooseProfilePictureFromPhone(){
        if(requestPermissionStorage(this)) {
            startActivityForResult(intentSinglePicture(), RC_CHOOSE_PHOTO)
        } else {
            return
        }
    }


    private fun savePicturePicked(data: Intent){
        urlInDevice = data.data?.toString()
        Glide.with(requireContext())
            .load(uriProfileImage)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.addAgentViewPictureAgent)

    }

    private fun showSnackBarMessage(message: String){
        val viewLayout = requireActivity().findViewById<CoordinatorLayout>(R.id.activity_main_layout)
        showSnackBar(viewLayout, message)

    }

    fun showSnackBar(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
            config(view.context)
            getView().findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
            show()
        }
    }

    fun Snackbar.config(context: Context){
        val params = this.view.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(12,12,12,12)
        this.view.layoutParams = params

        this.view.background = ContextCompat.getDrawable(context, R.drawable.bg_snackbar)

        ViewCompat.setElevation(this.view, 6f)
    }
}