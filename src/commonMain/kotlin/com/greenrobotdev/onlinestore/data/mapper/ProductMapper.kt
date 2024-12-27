package com.greenrobotdev.onlinestore.data.mapper

import com.greenrobotdev.onlinestore.data.model.ProductDTO
import com.greenrobotdev.onlinestore.domain.entity.Product

fun asDomainProductEntity(
    id: Long,
    title: String,
    price: Double,
    image: String,
    description: String,
    category: String,
    rate: Double,
    count: Long
) = Product(
    id = id,
    title = title,
    price = price,
    image = image,
    description = description,
    category = category,
    rate = rate,
    count = count.toDouble()
)

fun List<ProductDTO>?.asDomainMovieList(): List<Product> {
    return this?.map { it.asDomainModel() } ?: emptyList()
}

/** Convert remote response to [Product] Domain objects*/
fun ProductDTO.asDomainModel() = Product(
    id = id,
    title = title,
    price = price.toDouble(),
    image = image,
    description = description ?: "",
    category = category,
    rate = rating.rate.toDouble(),
    count = rating.count.toDouble()
)
