package com.openclassrooms.realestatemanager.data.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.models.PropertyPhoto
import com.openclassrooms.realestatemanager.utils.PHOTO_TABLE_NAME

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(photos: List<PropertyPhoto>)

    @Query("DELETE FROM $PHOTO_TABLE_NAME WHERE photo_id IN (:photoId)")
    suspend fun deletePictures(photoId: List<String>)

    @Update
    suspend fun updatePicture(photos: List<PropertyPhoto>)
}