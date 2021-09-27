package com.openclassrooms.realestatemanager.ui.addAgent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealEstateManagerApp
import com.openclassrooms.realestatemanager.data.Agent
import com.openclassrooms.realestatemanager.databinding.ActivityAddAgentBinding
import com.openclassrooms.realestatemanager.ui.viewModels.AddAgentViewModel
import com.openclassrooms.realestatemanager.ui.viewModels.AddAgentViewModelFactory
import com.openclassrooms.realestatemanager.utils.IMAGE_ONLY_TYPE
import com.openclassrooms.realestatemanager.utils.PERMS_EXT_STORAGE
import com.openclassrooms.realestatemanager.utils.RC_CHOOSE_PHOTO
import com.openclassrooms.realestatemanager.utils.RC_IMAGE_PERMS
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class AddAgentActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private var addAgentActivity: AddAgentActivity? = null

    private lateinit var activityAddAgentBinding: ActivityAddAgentBinding

    private val addAgentViewModel: AddAgentViewModel by viewModels {
        AddAgentViewModelFactory((application as RealEstateManagerApp).repository)
    }

    private var uriProfileImage: String? = null
    private var urlInDevice: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddAgentBinding = ActivityAddAgentBinding.inflate(layoutInflater)
        setContentView(activityAddAgentBinding.root)
        configureToolbar()
        createAgent()
    }

    // VIEW MODEL CONNECTION

    fun intentSinglePicture(): Intent {
        return Intent().apply {
            action = Intent.ACTION_PICK
            type = IMAGE_ONLY_TYPE
        }
    }


//    private fun savePicturePicked(data: Intent){
//        urlInDevice = data.data?.toString()
//        Glide.with(applicationContext)
//            .load(uriProfileImage)
//            .apply(RequestOptions.circleCropTransform())
//            .into(binding.addAgentViewPictureAgent)
//
//    }

    fun createAgent() {
        addAgentViewModel.insert(Agent(1, "AAA", "BBB", "CCC"))
    }

    private fun showSnackBarMessage(message: String) {
        val viewLayout = findViewById<CoordinatorLayout>(R.id.activity_main_layout)
        showSnackBar(viewLayout, message)

    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
            config(view.context)
            getView().findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines =
                5
            show()
        }
    }

    fun Snackbar.config(context: Context) {
        val params = this.view.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(12, 12, 12, 12)
        this.view.layoutParams = params

        this.view.background = ContextCompat.getDrawable(context, R.drawable.bg_snackbar)

        ViewCompat.setElevation(this.view, 6f)
    }

    private fun configureToolbar() {
        setSupportActionBar(activityAddAgentBinding.mainActivityToolbar.toolbar)
        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.close)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_agent_check_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add_agent_activity_check -> {
//                activityAddAgentBinding.mainActivityToolbar.toolbar.setOnClickListener()
                Toast.makeText(this, "Agent added", Toast.LENGTH_LONG)
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
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
        showSnackBarMessage(getString(R.string.allow_storage))
    }
}