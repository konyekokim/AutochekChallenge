package com.konyekokim.core.network.services

import com.konyekokim.core.network.responses.AllCarsResponse
import com.konyekokim.core.network.responses.CarDetailResponse
import com.konyekokim.core.network.responses.CarMediaListResponse
import com.konyekokim.core.network.responses.PopularCarsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CarService {

    @GET("inventory/make?popular=true")
    suspend fun fetchPopularCarBrands(): Response<PopularCarsResponse>

    @GET("inventory/car/search")
    suspend fun getAllCars(): Response<AllCarsResponse>

    @GET("inventory/car/{carId}")
    suspend fun getCarDetails(
        @Path("carId") carId: String
    ): Response<CarDetailResponse>

    @GET("inventory/car_media")
    suspend fun getCarMediaList(
        @Query("carId") carId: String
    ): Response<CarMediaListResponse>

}