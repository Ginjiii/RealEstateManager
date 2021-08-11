package com.openclassrooms.realestatemanager.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity(tableName = "agents")
@Parcelize
data class Agent (
        var firstName: String = "",
        var lastName: String = "",
        var username: String = "",
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "agents_id")
    var id: Int = 0
}