package com.openclassrooms.realestatemanager.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    const val REAL_ESTATE_DATABASE_NAME = "real_estate_db"

    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context) =
            Glide.with(context).setDefaultRequestOptions(
                    RequestOptions()
                            .placeholder(R.drawable.ic_placeholder)
                            .error(R.drawable.not_supported)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
            )

    @Singleton
    @Provides
    fun providePropertyRepository() = PropertyRepository()

    @Singleton
    @Provides
    fun provideRealEstateDatabase(@ApplicationContext context: Context) =
            Room.databaseBuilder(context, RealEstateDatabase::class.java, REAL_ESTATE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideAgentDao(database: RealEstateDatabase) = database.getAgentDao()

    @Singleton
    @Provides
    fun providePropertyDao(database: RealEstateDatabase) = database.getPropertyDao()

    @Singleton
    @Provides
    fun providePropertyPhotoDao(database: RealEstateDatabase) = database.getPropertyPhotoDao()
}