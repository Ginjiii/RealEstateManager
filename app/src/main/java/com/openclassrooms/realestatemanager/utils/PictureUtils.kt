package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun getThumbnailFromPicture(picture: String, context: Context): String{
    val picturePath = File(picture)
    val bitmap = MediaStore.Images.Media
        .getBitmap(context.contentResolver, Uri.fromFile(picturePath))
    return bitmap.saveToInternalStorage(
        context, generateName(), TypeImage.PROPERTY ).toString()

}

fun getPicturesPathFromData(data: Intent): List<String>{
    val listPicture = mutableListOf<String>()
    val uris = data.clipData
    if(uris != null) {
        for(i in 0 until uris.itemCount){
            val uri = uris.getItemAt(i).uri
            listPicture.add(uri.toString())
        }
    } else {
        val uri = data.data
        uri?.let{listPicture.add(it.toString())}
    }

    return listPicture
}

fun filePathToInternalStorage(context: Context, name: String, type: TypeImage): File {
    val directory = context.getExternalFilesDir(type.folder)
    return File(directory, "JPEG_${type}_$name.jpeg")
}

fun generateName(): String{
    return SimpleDateFormat(DATE_FORMAT_FOR_NAME).format(Date())
}
