package com.openclassrooms.realestatemanager.data.injections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.data.repositories.AgentRepository
import com.openclassrooms.realestatemanager.ui.viewModels.AddAgentViewModel

class ViewModelFactory( private val agentRepository: AgentRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AddAgentViewModel::class.java) -> AddAgentViewModel(agentRepository) as T

            else -> throw Exception("Unknown ViewModel class")

        }
    }
}