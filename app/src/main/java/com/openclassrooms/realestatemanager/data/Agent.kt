package com.openclassrooms.realestatemanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.utils.AGENT_TABLE_NAME

@Entity(tableName = AGENT_TABLE_NAME)
data class Agent (
    @PrimaryKey @ColumnInfo(name = "agent_id")val id: String = "",
    @ColumnInfo(name = "first_name")val firstName: String = "",
    @ColumnInfo(name = "last_name")val lastName: String = "",
    val email: String = "",
    @ColumnInfo(name = "phone_number")val phoneNumber: String = "")

