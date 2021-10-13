package com.openclassrooms.realestatemanager.utils

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun filePathToInternalStorage(context: Context, name: String, type: TypeImage): File {
    val directory = context.getExternalFilesDir(type.folder)
    return File(directory, "JPEG_${type}_$name.jpeg")
}

fun generateName(): String{
    return SimpleDateFormat(DATE_FORMAT_FOR_NAME).format(Date())
}
