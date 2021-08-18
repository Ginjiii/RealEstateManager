package com.openclassrooms.realestatemanager.ui.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.models.PropertyWithAllData
import com.openclassrooms.realestatemanager.repositories.ModelRepository
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val modelRepository: ModelRepository,
) : ViewModel() {

    fun getViewStateLiveData() : LiveData<PropertyWithAllData> {
        return Transformations.switchMap(propertyRepository.getCurrentPropertyIdLiveData()) { id ->
            modelRepository.getPropertyForId(id)
        }
    }

    val getAllProperties: LiveData<List<PropertyWithAllData>> = modelRepository.observeAllProperties()

    fun setCurrentPropertyId(id: Int) {
        propertyRepository.setCurrentPropertyId(id)
    }

}