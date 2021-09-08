package com.openclassrooms.realestatemanager.data.injections

import android.content.Context
import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.data.repositories.AgentRepository

class Injection {

    companion object {

        private fun providesAgentRepository(context: Context): AgentRepository {
            val database = RealEstateManagerDatabase.getDatabase(context)
            return AgentRepository.getAgentRepository(database.agentDao())
        }

        fun providesViewModelFactory(context: Context): ViewModelFactory {
            val agentRepository = providesAgentRepository(context)
            return ViewModelFactory(agentRepository)
        }
    }
}