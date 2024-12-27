package com.greenrobotdev.onlinestore.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("price") val price: Float,
    @SerialName("description") val description: String?,
    @SerialName("category") val category: String,
    @SerialName("image") val image: String,
    @SerialName("rating") val rating: RatingData,
) {
    companion object {
        val mocked = ProductDTO(
            id = 1, "Jersey",
            price = 250.0f,
            image = "",
            description = "argentina jersey",
            category = "Cloth",
            rating = RatingData(count = 100, rate = 4.5f)
        )
    }
}


@Serializable
data class RatingData(
    @SerialName("count") val count: Int,
    @SerialName("rate") val rate: Float,
)