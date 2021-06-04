package com.konyekokim.core.repositories

import com.konyekokim.core.data.Result
import com.konyekokim.core.data.entities.Car
import com.konyekokim.core.datasource.local.CarLocalDataSource
import com.konyekokim.core.datasource.remote.CarRemoteDataSource
import com.konyekokim.core.mappers.AllCarResponseToCarMapper
import com.konyekokim.core.network.responses.CarDetailResponse
import com.konyekokim.core.network.responses.CarMediaListItem
import com.konyekokim.core.network.responses.PopularCarsResponseItem
import javax.inject.Inject

class CarRepository @Inject constructor(
    private val localDataSource: CarLocalDataSource,
    private val remoteDataSource: CarRemoteDataSource,
    private val carMapper: AllCarResponseToCarMapper
) {

    suspend fun fetchPopularCarBrands(): Result<List<PopularCarsResponseItem>>{
        return when (val response = remoteDataSource.fetchPopularCarBrands()){
            is Result.Success -> {
                Result.Success(response.data)
            }
            is Result.Error -> response
        }
    }

    suspend fun getAllCars(page: Int): Result<List<Car>> {
        return when (val response = remoteDataSource.getAllCars(page)) {
            is Result.Success -> {
                val cars = carMapper.map(response.data)
                localDataSource.insertAll(cars)
                Result.Success(cars)
            }
            is Result.Error -> response
        }
    }

    suspend fun getCarById(carId: String) = localDataSource.getCarById(carId)

    suspend fun getCarDetails(carId: String): Result<CarDetailResponse> {
        return  when (val response = remoteDataSource.getCarDetails(carId)){
            is Result.Success -> {
                Result.Success(response.data)
            }
            is Result.Error -> response
        }
    }

    suspend fun getCarMedialList(carId: String): Result<List<CarMediaListItem>> {
        return when (val response = remoteDataSource.getCarMediaList(carId)){
            is Result.Success -> {
                Result.Success(response.data)
            }
            is Result.Error -> response
        }
    }

}