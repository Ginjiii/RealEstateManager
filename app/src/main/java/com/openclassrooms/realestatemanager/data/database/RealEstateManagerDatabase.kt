package com.openclassrooms.realestatemanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.data.Agent
import com.openclassrooms.realestatemanager.data.dao.AgentDao
import com.openclassrooms.realestatemanager.data.dao.PropertyDao
import com.openclassrooms.realestatemanager.models.Property


@Database(entities = [Agent::class, Property::class], version = 1, exportSchema = false)

abstract class RealEstateManagerDatabase : RoomDatabase() {

    abstract fun agentDao(): AgentDao
    abstract fun propertyDao(): PropertyDao

    companion object {
        @Volatile
        private var INSTANCE: RealEstateManagerDatabase? = null

        fun getDatabase(context: Context): RealEstateManagerDatabase {
            return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RealEstateManagerDatabase::class.java,
                        "RealEstateManager_database"
                    ).build()
                    INSTANCE = instance
                    instance
                }
        }
    }
}