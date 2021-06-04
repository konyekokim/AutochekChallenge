package com.konyekokim.core.network.responses

data class AllCarsResponseItem(
    val id: String,
    val title: String,
    val imageUrl: String,
    val gradeScore: Double,
    val marketplacePrice: Int,
)