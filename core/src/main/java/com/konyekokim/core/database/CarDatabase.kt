package com.konyekokim.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.konyekokim.core.BuildConfig
import com.konyekokim.core.data.entities.Car

@Database(
    entities = [Car::class],
    exportSchema = false,
    version = BuildConfig.CAR_DATABASE_VERSION
)

abstract class CarDatabase: RoomDatabase() {
    abstract fun carDao(): CarDao
}