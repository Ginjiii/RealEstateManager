package com.openclassrooms.realestatemanager.data.repositories


import com.openclassrooms.realestatemanager.data.dao.PropertyDao
import com.openclassrooms.realestatemanager.models.Property
import kotlinx.coroutines.flow.Flow

class PropertyRepository(private val propertyDao: PropertyDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allProperties: Flow<List<Property>> = propertyDao.getAllProperties()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
    suspend fun insert(property: Property) {
        propertyDao.createProperty(property)
    }

    suspend fun getProperty(idProperty: String) = propertyDao.getProperty(idProperty)
}