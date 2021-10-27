package com.openclassrooms.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.data.Agent
import com.openclassrooms.realestatemanager.utils.AGENT_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentDao {
    @Query("SELECT * FROM $AGENT_TABLE_NAME")
    fun getAllAgents(): Flow<List<Agent>>

    @Query("SELECT * FROM $AGENT_TABLE_NAME WHERE agent_id = :agentId")
    suspend fun getAgent(agentId: String): List<Agent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAgent(agent: Agent)

    @Query("SELECT COUNT(*) FROM $AGENT_TABLE_NAME")
    fun getRowCount(): Int

}