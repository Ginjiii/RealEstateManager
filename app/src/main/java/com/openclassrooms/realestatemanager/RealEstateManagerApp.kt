package com.openclassrooms.realestatemanager

import android.app.Application
import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.data.repositories.AgentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RealEstateManagerApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { RealEstateManagerDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { AgentRepository(database.agentDao()) }
}