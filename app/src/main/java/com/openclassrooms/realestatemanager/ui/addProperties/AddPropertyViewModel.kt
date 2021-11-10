package com.openclassrooms.realestatemanager.ui.addProperties

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.repositories.AgentRepository
import com.openclassrooms.realestatemanager.data.repositories.PropertyRepository
import com.openclassrooms.realestatemanager.models.Property
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddPropertyViewModel(private val repository: PropertyRepository, private val agentRepository: AgentRepository) : ViewModel() {



    val allProperties: LiveData<List<Property>> = repository.allProperties.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(property: Property) = viewModelScope.launch {
        repository.insert(property)
    }

    fun checkAgent(): Job = viewModelScope.launch {
        agentRepository.getCountAgent()
    }
}

class AddPropertyViewModelFactory(private val repository: PropertyRepository, private val agentRepository: AgentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPropertyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddPropertyViewModel(repository, agentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}