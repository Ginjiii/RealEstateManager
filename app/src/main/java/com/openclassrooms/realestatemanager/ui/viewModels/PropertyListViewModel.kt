package com.openclassrooms.realestatemanager.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.models.PropertyWithAllData
import com.openclassrooms.realestatemanager.repositories.ModelRepository
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import javax.inject.Inject

class PropertyListViewModel @Inject constructor(
        private val propertyRepository: PropertyRepository,
        modelRepository: ModelRepository,
) : ViewModel() {

    val getAllProperties: LiveData<List<PropertyWithAllData>> = modelRepository.observeAllProperties()

    fun setCurrentPropertyId(id: Int) {
        propertyRepository.setCurrentPropertyId(id)
    }
}