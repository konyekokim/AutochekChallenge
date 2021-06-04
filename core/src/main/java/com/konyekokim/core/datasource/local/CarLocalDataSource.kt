package com.konyekokim.core.datasource.local

import com.konyekokim.core.data.entities.Car
import com.konyekokim.core.database.CarDao
import javax.inject.Inject

class CarLocalDataSource @Inject constructor(
    private val carDao: CarDao
) {

    suspend fun insertAll (cars: List<Car>){
        carDao.insertAll(cars)
    }

    suspend fun getCarById(carId: String) = carDao.getCarById(carId)

}