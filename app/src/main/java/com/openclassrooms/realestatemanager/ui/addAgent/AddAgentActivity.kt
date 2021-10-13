package com.openclassrooms.realestatemanager.ui.addAgent


import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealEstateManagerApp
import com.openclassrooms.realestatemanager.data.Agent
import com.openclassrooms.realestatemanager.databinding.ActivityAddAgentBinding
import com.openclassrooms.realestatemanager.ui.viewModels.AddAgentViewModel
import com.openclassrooms.realestatemanager.ui.viewModels.AddAgentViewModelFactory
import com.openclassrooms.realestatemanager.utils.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class AddAgentActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {


    private lateinit var binding: ActivityAddAgentBinding

    private val addAgentViewModel: AddAgentViewModel by viewModels {
        AddAgentViewModelFactory((application as RealEstateManagerApp).repository)
    }

    private var uriProfileImage: String? = null
    private var urlInDevice: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAgentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddAgent.setOnClickListener {
            saveAgent()
        }
        binding.addAgentViewPictureAgent.setOnClickListener {
            chooseProfilePictureFromPhone()
        }
    }

    // VIEW MODEL CONNECTION

    fun intentSinglePicture(): Intent {
        return Intent().apply {
            action = Intent.ACTION_PICK
            type = IMAGE_ONLY_TYPE
        }
    }

    private fun saveAgent(){
        val agent = Agent(firstName = binding.addAgentViewFirstname.text.toString(),
            lastName = binding.addAgentViewLastname.text.toString(),
            email = binding.addAgentViewEmail.text.toString(),
            phoneNumber = binding.addAgentViewPhonenb.text.toString())
        addAgentViewModel.insert(agent)
        Toast.makeText(this, "Agent added", Toast.LENGTH_LONG)
            .show()
    }


    private fun savePicturePicked(data: Intent){
        urlInDevice = data.data?.toString()
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
        val uriInternal = bitmap.saveToInternalStorage(
          applicationContext, generateName(), TypeImage.AGENT
        )
        uriProfileImage = uriInternal.toString()
        Glide.with(applicationContext)
            .load(uriProfileImage)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.addAgentViewPictureAgent)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_CHOOSE_PHOTO){
            if(resultCode == RESULT_OK){
                data?.let{
                    savePicturePicked(it)
                }
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == RC_IMAGE_PERMS) chooseProfilePictureFromPhone()
    }

    private fun chooseProfilePictureFromPhone() {
        if (requestPermissionStorage(this)) {
            startActivityForResult(intentSinglePicture(), RC_CHOOSE_PHOTO)
        } else {
            return
        }
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    private fun requestPermissionStorage(addAgentActivity: AddAgentActivity): Boolean {
        if (!EasyPermissions.hasPermissions(addAgentActivity!!, PERMS_EXT_STORAGE)) {
            EasyPermissions.requestPermissions(
                addAgentActivity,
                addAgentActivity!!.getString(R.string.storage_perm_request),
                RC_IMAGE_PERMS,
                PERMS_EXT_STORAGE
            )
            return (EasyPermissions.hasPermissions(addAgentActivity!!, PERMS_EXT_STORAGE))
        }

        return true
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "Allow storage", Toast.LENGTH_LONG).show()
    }
}