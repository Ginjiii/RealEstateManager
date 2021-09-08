package com.openclassrooms.realestatemanager.models




data class Property(

        var id: Int = 0,
        var type: String,
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
        var labelPhoto: String)
