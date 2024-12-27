package com.greenrobotdev.onlinestore.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Long,
    val title: String,
    val price: Double,
    val image : String,
    val description: String,
    val category: String,
    val rate: Double,
    val count: Double
)
