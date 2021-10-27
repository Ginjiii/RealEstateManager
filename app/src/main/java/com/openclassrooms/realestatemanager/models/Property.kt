package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.data.Agent
import com.openclassrooms.realestatemanager.utils.PROPERTY_TABLE_NAME


@Entity(
        tableName = PROPERTY_TABLE_NAME,
        foreignKeys = [
                ForeignKey(
                        entity = Agent::class,
                        parentColumns = ["agent_id"],
                        childColumns = ["agent"],
                        onDelete = ForeignKey.NO_ACTION)])

data class Property(
        @ColumnInfo(name = "property_id") @PrimaryKey var id: String = "",
        @ColumnInfo(name = "type_property")var type:  TypeProperty = TypeProperty.HOUSE,
        var priceInDollars: Int,
        var areaInMeters: Int = 0,
        var nbrRoom: Int = 0,
        var nbrBedroom: Int = 0,
        var nbrBathroom: Int = 0,
        var description: String = "",
        var street: String,
        var postcode: String,
        var city: String,
        var country: String,
        var isSold: Boolean = false,
        var availableDate: String,
        var soldDate: String = "",
        var agentId: Int,
        var coverPhoto: String,
        var labelPhoto: String,
        var agent: String)

enum class TypeProperty(val typeName: String) {
        FLAT("Flat"),
        TOWNHOUSE("Townhouse"),
        PENTHOUSE("Penthouse"),
        HOUSE("House"),
        DUPLEX("Duplex")

}
