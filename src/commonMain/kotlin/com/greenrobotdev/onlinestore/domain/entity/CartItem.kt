package com.greenrobotdev.onlinestore.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val quantity: Int,
    val product: Product,
)
