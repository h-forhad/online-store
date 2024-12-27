package com.greenrobotdev.onlinestore.screen.cart

import com.greenrobotdev.onlinestore.domain.entity.CartItem
import kotlinx.serialization.Serializable

val nothing: Nothing? = null

@Serializable
data class CartState(
  val cartItems: List<CartItem>? = nothing,
  val selectedCarts: List<CartItem> = emptyList(),
)

sealed interface CartEvent {
  data class OnSelect(val cartItem: CartItem, val isSelected: Boolean) : CartEvent
  data class OnIncreaseQt(val cartItem: CartItem) : CartEvent
  data class OnDecreaseQt(val cartItem: CartItem) : CartEvent
  data class OnRemove(val cartItem: CartItem) : CartEvent
}
