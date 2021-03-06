package com.openclassrooms.realestatemanager.data.repositories

import com.openclassrooms.realestatemanager.data.Agent
import com.openclassrooms.realestatemanager.data.dao.AgentDao
import kotlinx.coroutines.flow.Flow

class AgentRepository (private val agentDao: AgentDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allAgents: Flow<List<Agent>> = agentDao.getAllAgents()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
    suspend fun insert(agent: Agent) {
        agentDao.createAgent(agent)
    }

    suspend fun getAgent(agentId: String) : List<Agent> {
        return agentDao.getAgent(agentId)
    }

    suspend fun getCountAgent() : Int {
        return agentDao.getRowCount()
    }
}