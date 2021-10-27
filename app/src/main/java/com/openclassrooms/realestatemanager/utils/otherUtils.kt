package com.openclassrooms.realestatemanager.utils

import java.util.*


var idGenerated: String = ""
    get() {
        field = UUID.randomUUID().toString()
        return field
    }