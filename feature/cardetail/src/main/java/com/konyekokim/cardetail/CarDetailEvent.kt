package com.konyekokim.cardetail

sealed class CarDetailEvent {
    data class OnMediaItemClicked(
        val carImageUrl: String
    ): CarDetailEvent()
}