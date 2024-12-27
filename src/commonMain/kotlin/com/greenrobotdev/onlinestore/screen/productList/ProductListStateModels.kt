package com.greenrobotdev.onlinestore.screen.productList

import com.greenrobotdev.onlinestore.domain.entity.Product
import kotlinx.serialization.Serializable

val Loading: Nothing? = null

@Serializable
data class ProductListState(
  val products: List<Product>? = Loading,
  val numberOfFavorite : Int = 0,
  val numberOfCartProducts : Int = 0,
  val isRefreshing  : Boolean = false,
  val hasError: Boolean? = null
)

sealed interface ProductListEvent {
  object Refresh: ProductListEvent
  data class Update(val string: String): ProductListEvent
}
