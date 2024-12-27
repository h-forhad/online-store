package com.greenrobotdev.onlinestore.screen.home

import com.greenrobotdev.onlinestore.domain.entity.Product
import kotlinx.serialization.Serializable

@Serializable
sealed class StoryHomeScreen {
  object List: StoryHomeScreen()

  data class   Details(val product: Product): StoryHomeScreen()

  object Wishlist: StoryHomeScreen()
  object Cart: StoryHomeScreen()
}
