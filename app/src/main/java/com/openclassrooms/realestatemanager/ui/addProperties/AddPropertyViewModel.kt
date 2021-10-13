package com.openclassrooms.realestatemanager.ui.addProperties

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.Agent
import com.openclassrooms.realestatemanager.data.repositories.AgentRepository
import com.openclassrooms.realestatemanager.data.repositories.PropertyRepository
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.ui.viewModels.AddAgentViewModel
import kotlinx.coroutines.launch

class AddPropertyViewModel(private val repository: PropertyRepository) : ViewModel() {

    val allProperties: LiveData<List<Property>> = repository.allProperties.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(property: Property) = viewModelScope.launch {
        repository.insert(property)
    }
}

class AddPropertyViewModelFactory(private val repository: AgentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAgentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddAgentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}