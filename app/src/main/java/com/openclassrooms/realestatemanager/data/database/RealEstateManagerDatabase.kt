package com.openclassrooms.realestatemanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.data.Agent
import com.openclassrooms.realestatemanager.data.dao.AgentDao
import kotlinx.coroutines.CoroutineScope


@Database(entities = [Agent::class], version = 1, exportSchema = false)

abstract class RealEstateManagerDatabase : RoomDatabase() {

    abstract fun agentDao(): AgentDao

    companion object {
        @Volatile
        private var INSTANCE: RealEstateManagerDatabase? = null

        fun getDatabase(context: Context, applicationScope: CoroutineScope): RealEstateManagerDatabase {
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