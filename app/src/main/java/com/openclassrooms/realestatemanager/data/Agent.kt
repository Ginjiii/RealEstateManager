package com.openclassrooms.realestatemanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.utils.AGENT_TABLE_NAME

@Entity(tableName = AGENT_TABLE_NAME)
data class Agent (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "agent_id") val id: Int,
    @ColumnInfo(name = "first_name")var firstName: String = "",
    @ColumnInfo(name = "last_name")var lastName: String = "",
    var username: String = "")

