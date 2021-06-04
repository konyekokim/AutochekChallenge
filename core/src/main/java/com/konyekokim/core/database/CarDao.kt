package com.konyekokim.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.konyekokim.core.data.entities.Car

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Car>)

    @Query("SELECT * FROM Car WHERE carId = :carId LIMIT 1")
    suspend fun getCarById(carId: Int): Car?

}