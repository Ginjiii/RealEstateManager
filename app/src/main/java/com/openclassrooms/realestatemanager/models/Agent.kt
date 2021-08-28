package com.openclassrooms.realestatemanager.models


//@Entity(tableName = "agents")
//@Parcelize
data class Agent (
        var firstName: String = "",
        var lastName: String = "",
        var username: String = "",
//) : Parcelable {
//    @IgnoredOnParcel
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "agents_id")
    var id: Int = 0)
//}