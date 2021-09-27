package com.openclassrooms.realestatemanager

import android.app.Application
import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.data.repositories.AgentRepository

class RealEstateManagerApp : Application() {
    val database by lazy { RealEstateManagerDatabase.getDatabase(this) }
    val repository by lazy { AgentRepository(database.agentDao()) }
}