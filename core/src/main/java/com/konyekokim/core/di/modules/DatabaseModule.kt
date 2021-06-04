package com.konyekokim.core.di.modules

import android.content.Context
import androidx.room.Room
import com.konyekokim.core.BuildConfig
import com.konyekokim.core.database.CarDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCarDatabase(context: Context) =
        Room.databaseBuilder(
            context,
            CarDatabase::class.java,
            BuildConfig.CAR_DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideCarDao(carDatabase: CarDatabase) = carDatabase.carDao()

}