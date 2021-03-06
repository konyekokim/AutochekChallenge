package com.konyekokim.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class Car(
    @PrimaryKey
    val id: String,
    val title: String?,
    val imageUrl: String?,
    val gradeScore: Double?,
    val marketplacePrice: Int?,
)