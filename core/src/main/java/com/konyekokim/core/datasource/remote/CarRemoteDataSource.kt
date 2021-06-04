package com.konyekokim.core.datasource.remote

import com.konyekokim.core.data.Result
import com.konyekokim.core.network.responses.*
import com.konyekokim.core.network.services.CarService
import retrofit2.Response
import javax.inject.Inject

class CarRemoteDataSource @Inject constructor(
    private val carService: CarService
){

    suspend fun fetchPopularCarBrands(): Result<List<PopularCarsResponseItem>>{
        return try {
            val response = carService.fetchPopularCarBrands()
            getPopularCarBrandsResult(response = response, onError = {
                Result.Error("Error Fetching Car Brands ${response.code()} ${response.message()}")
            })
        } catch (e: Exception){
            Result.Error("Error fetching Car Brands")
        }
    }

    private inline fun getPopularCarBrandsResult(
        response: Response<PopularCarsResponse>,
        onError: () -> Result.Error
    ): Result<List<PopularCarsResponseItem>> {
        if(response.isSuccessful){
            val body = response.body()
            if(body != null) {
                return Result.Success(body.makeList)
            }
        }
        return onError.invoke()
    }

    suspend fun getAllCars(): Result<List<AllCarsResponseItem>>{
        return try {
            val response = carService.getAllCars()
            getAllCarsResult(response = response, onError = {
                Result.Error("Error Fetching All Cars ${response.code()} ${response.message()}")
            })
        } catch (e: Exception){
            Result.Error("Error fetching All Cars")
        }
    }

    private inline fun getAllCarsResult(
        response: Response<AllCarsResponse>,
        onError: () -> Result.Error
    ): Result<List<AllCarsResponseItem>> {
        if(response.isSuccessful){
            val body = response.body()
            if(body != null) {
                return Result.Success(body.result)
            }
        }
        return onError.invoke()
    }

    suspend fun getCarDetails(carId: String): Result<CarDetailResponse>{
        return try {
            val response = carService.getCarDetails(carId)
            getCarDetailsResult(response = response, onError = {
                Result.Error("Error getting Car Details ${response.code()} ${response.message()}")
            })
        } catch (e: Exception){
            Result.Error("Error getting Car Details")
        }
    }

    private inline fun getCarDetailsResult(
        response: Response<CarDetailResponse>,
        onError: () -> Result.Error
    ): Result<CarDetailResponse> {
        if(response.isSuccessful){
            val body = response.body()
            if(body != null) {
                return Result.Success(body)
            }
        }
        return onError.invoke()
    }

    suspend fun getCarMediaList(carId: String): Result<List<CarMediaListItem>>{
        return try {
            val response = carService.getCarMediaList(carId)
            getCarMediaListResult(response = response, onError = {
                Result.Error("Error getting Car Media List ${response.code()} ${response.message()}")
            })
        } catch (e: Exception){
            Result.Error("Error getting Car Media List")
        }
    }

    private inline fun getCarMediaListResult(
        response: Response<CarMediaListResponse>,
        onError: () -> Result.Error
    ): Result<List<CarMediaListItem>> {
        if(response.isSuccessful){
            val body = response.body()
            if(body != null) {
                return Result.Success(body.carMediaList)
            }
        }
        return onError.invoke()
    }

}