package com.konyekokim.core.network.responses

data class CarDetailResponse(
    val id: String,
    val year: Int,
    val mileage: Int,
    val mileageUnit: String,
    val marketplacePrice: Int,
    val interiorColor: String,
    val exteriorColor: String,
    val engineType: String,
    val gradeScore: Double,
    val transmission: String,
    val city: String,
    val imageUrl: String,
    val reasonForSelling: String,
    val sellingCondition: String,
    val model: CarModelDetail
)

data class CarModelDetail(
    val make: CarMake,
    val name: String,
    val series: String
)

data class CarMake(
    val name: String,
    val imageUrl: String
)

data class CarBodyType(
    val name: String
)