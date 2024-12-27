package com.greenrobotdev.onlinestore.screen.productDetails

import com.greenrobotdev.onlinestore.domain.entity.Product
import kotlinx.serialization.Serializable

val Loading: Nothing? = null
val DontKnowYet: Nothing? = null

@Serializable
data class ProductDetailsState(
  val product: Product? = Loading,
  val isSaved: Boolean = false,
  val numberOfProduct : Int = 0,
)


sealed interface ProductDetailsEvent {
  object OnFavoritePressed: ProductDetailsEvent
  object OnAddToCartPressed: ProductDetailsEvent
  object OnDecreaseCountPressed: ProductDetailsEvent
  object OnIncreaseCountPressed: ProductDetailsEvent
}


