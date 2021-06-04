package com.konyekokim.core.mappers

import com.konyekokim.core.data.entities.Car
import com.konyekokim.core.network.responses.AllCarsResponseItem
import javax.inject.Inject

class AllCarResponseToCarMapper @Inject constructor() :
    Mapper<List<AllCarsResponseItem>, List<Car>>{

    override suspend fun map(from: List<AllCarsResponseItem>): List<Car> {
        return from.map {
            Car(
                id = it.id,
                title = it.title,
                imageUrl = it.imageUrl,
                gradeScore = it.gradeScore,
                marketplacePrice = it.marketplacePrice
            )
        }
    }
}