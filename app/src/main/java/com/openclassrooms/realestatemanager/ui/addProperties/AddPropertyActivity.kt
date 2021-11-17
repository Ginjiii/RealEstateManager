package com.openclassrooms.realestatemanager.ui.addProperties


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealEstateManagerApp
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.models.TypeProperty
import com.openclassrooms.realestatemanager.utils.ACTION_TYPE_ADD_PROPERTY
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddPropertyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPropertyBinding
    private lateinit var actionType: String

    private var formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

    private var currentAgentId: Int = 0
    private lateinit var agent: String
    private lateinit var type: String
    private var room: Int = 0
    private var bedroom: Int = 0
    private var bathroom: Int = 0
    private var croppedPhoto: String? = null
    private lateinit var labelPhoto: String

    private val addPropertyViewModel: AddPropertyViewModel by viewModels {
        AddPropertyViewModelFactory((application as RealEstateManagerApp).repository, (application as RealEstateManagerApp).agentRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureActionType()
        setupTypeSpinner()
        setupRoomsSpinner()

        binding.availableDate.setOnClickListener { showDatePickerDialog(binding.availableDate) }
        binding.buttonAddProperty.setOnClickListener {
            lifecycleScope.launch {
                if (addPropertyViewModel.checkAgent()) {
                    val intent = Intent(applicationContext, AddPropertyActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, getString(R.string.create_agent_first), Toast.LENGTH_LONG).show()
                }
                Log.d("tagii","act: " + addPropertyViewModel.checkAgent() )}
        }
    }

    private fun configureActionType() {
        actionType = intent.getStringExtra(ACTION_TYPE_ADD_PROPERTY).toString()
    }

    // Validate necessary fields
//    private fun confirmValidation() {
//        if (!validateType()
//            or (!Utils.validateInputFieldIfNullOrEmpty(binding.price, "Can't be empty"))
//            or (!Utils.validateInputFieldIfNullOrEmpty(binding.street, "Can't be empty"))
//            or (!Utils.validateInputFieldIfNullOrEmpty(binding.postcode, "Can't be empty"))
//            or (!Utils.validateInputFieldIfNullOrEmpty(binding.etCity, "Can't be empty"))
//            or (!Utils.validateInputFieldIfNullOrEmpty(binding.etCountry, "Can't be empty"))
//            or (!Utils.validateInputFieldIfNullOrEmpty(binding.availableDate, "Can't be empty"))
//        ) Toast.makeText(
//            applicationContext,
//            "Please fill all the required fields",
//            Toast.LENGTH_SHORT
//        ).show()
//        else saveProperty()
//    }

    // Validate property type spinner
//    private fun validateType(): Boolean {
//        val errorText: TextView = binding.spType.selectedView as TextView
//
//        return if (binding.spType.selectedItem.toString() == "Type") {
//            errorText.error = "Choose a type"
//            false
//        } else {
//            errorText.error = null
//            true
//        }
//    }

    private fun saveProperty() {
        val property = Property(
            type = TypeProperty.HOUSE,
            priceInDollars = binding.price.text.toString().toInt(),
            areaInMeters = binding.area.text.toString().toInt(),
            nbrRoom = binding.room.text.toString().toInt(),
            nbrBedroom = binding.bedroom.text.toString().toInt(),
            nbrBathroom = binding.bathroom.text.toString().toInt(),
            description = binding.description.text.toString(),
            street = binding.street.text.toString(),
            postcode = binding.postcode.text.toString(),
            city = binding.etCity.text.toString(),
            country = binding.etCountry.text.toString(),
            availableDate = binding.availableDate.text.toString(),
            agentId = currentAgentId,
            coverPhoto = croppedPhoto!!,
            labelPhoto = labelPhoto,
            agent = binding.addPropertyViewDropdownAgent.toString())
        addPropertyViewModel.insert(property)
        Toast.makeText(this, "Property added", Toast.LENGTH_LONG)
            .show()
    }

    // Setup Type Spinner
    private fun setupTypeSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            applicationContext,
            R.array.type_of_properties,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spType.adapter = adapter

        binding.spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent!!.getItemAtPosition(position)
                if (position > 0) {
                    type = selectedItem.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    // Setup Rooms Spinners
    private fun setupRoomsSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            applicationContext,
            R.array.number_of_rooms,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.roomSpinner.adapter = adapter
        binding.bedroomSpinner.adapter = adapter
        binding.bathroomSpinner.adapter = adapter

        binding.roomSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent!!.getItemAtPosition(position)
                val text = selectedItem.toString()
                room = text.replace("+", "").toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.bedroomSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent!!.getItemAtPosition(position)
                    val text = selectedItem.toString()
                    bedroom = text.replace("+", "").toInt()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        binding.bathroomSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent!!.getItemAtPosition(position)
                    val text = selectedItem.toString()
                    bathroom = text.replace("+", "").toInt()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    // Setup DatePicker
    private fun showDatePickerDialog(editText: AppCompatEditText) {
        val getDate = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            applicationContext,
            { _, year, month, day ->

                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, year)
                selectDate.set(Calendar.MONTH, month)
                selectDate.set(Calendar.DAY_OF_MONTH, day)

                val date = formatDate.format(selectDate.time)
                editText.setText(date)

            },
            getDate.get(Calendar.YEAR),
            getDate.get(Calendar.MONTH),
            getDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()

        datePicker.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
        datePicker.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
    }
}