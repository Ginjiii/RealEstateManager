package com.openclassrooms.realestatemanager.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.utils.PROPERTY_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {
    @Query("SELECT * FROM $PROPERTY_TABLE_NAME")
    fun getAllProperties(): Flow<List<Property>>

    @Query("SELECT * FROM $PROPERTY_TABLE_NAME WHERE property_id = :propertyId")
     suspend fun getProperty(propertyId: String): List<Property>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun createProperty(property: Property)

}