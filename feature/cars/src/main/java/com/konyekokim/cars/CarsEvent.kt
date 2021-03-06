package com.konyekokim.cars

import com.konyekokim.core.data.entities.Car

sealed class CarsEvent {
    data class OpenCarDetail(
        val carId: String, val carName: String
    ): CarsEvent()
}