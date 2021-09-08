package com.openclassrooms.realestatemanager.models

import com.openclassrooms.realestatemanager.data.Agent

data class PropertyWithAllData(
        val property: Property,
        val agent: Agent
)
