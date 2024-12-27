package com.greenrobotdev.onlinestore.screen.wishlist

import com.greenrobotdev.onlinestore.domain.entity.Product
import kotlinx.serialization.Serializable

val nothing: Nothing? = null

@Serializable
data class WishListState(
  val products: List<Product>? = nothing,
)

sealed interface WishListEvent {
  data class onSelect(val product : Product): WishListEvent
}
