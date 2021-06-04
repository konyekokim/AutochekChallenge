package com.konyekokim.core.network.responses

data class PopularCarsResponseItem (
    val id: Int,
    val name: String,
    val imageUrl: String
)

data class PopularCarsResponse(
    val makeList: List<PopularCarsResponseItem>
)