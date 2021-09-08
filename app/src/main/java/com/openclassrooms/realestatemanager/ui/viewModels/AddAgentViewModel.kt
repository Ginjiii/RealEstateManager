package com.openclassrooms.realestatemanager.ui.viewModels

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.Agent
import com.openclassrooms.realestatemanager.data.repositories.AgentRepository
import kotlinx.coroutines.launch

class AddAgentViewModel(private val repository: AgentRepository) : ViewModel() {

    // Using LiveData and caching what allAgents returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allAgents: LiveData<List<Agent>> = repository.allAgents.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(agent: Agent) = viewModelScope.launch {
        repository.insert(agent)
    }
}

class AddAgentViewModelFactory(private val repository: AgentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAgentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddAgentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}