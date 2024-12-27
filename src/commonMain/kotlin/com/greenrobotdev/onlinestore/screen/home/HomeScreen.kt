package com.greenrobotdev.onlinestore.screen.home

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.greenrobotdev.onlinestore.screen.cart.CartScreen
import com.greenrobotdev.onlinestore.screen.home.StoryHomeScreen.Details
import com.greenrobotdev.onlinestore.screen.home.StoryHomeScreen.List
import com.greenrobotdev.onlinestore.screen.home.StoryHomeScreen.Wishlist
import com.greenrobotdev.onlinestore.screen.productDetails.ProductDetailsScreen
import com.greenrobotdev.onlinestore.screen.productList.ProductListScreen
import com.greenrobotdev.onlinestore.screen.wishlist.WishlistScreen
import io.github.xxfast.decompose.router.stack.RoutedContent
import io.github.xxfast.decompose.router.stack.Router
import io.github.xxfast.decompose.router.stack.rememberRouter

@Composable
fun HomeScreen() {
    val router: Router<StoryHomeScreen> = rememberRouter(
        type = StoryHomeScreen::class,
        initialStack = { listOf<StoryHomeScreen>(List) }
    )

    RoutedContent(
        router = router,
        animation = stackAnimation(slide())
    ) { screen ->
        when (screen) {
            List -> ProductListScreen(
                onProductSelect = { router.push(Details(it)) },
                wishlistSelect = { router.push(Wishlist) },
                cartSelected = { router.push(StoryHomeScreen.Cart) }
            )

            is Details -> ProductDetailsScreen(
                product = screen.product,
                onBack = { router.pop() }
            )

            is Wishlist -> WishlistScreen(
                onBack = { router.pop() },
                onProductSelect = { router.push(Details(it)) }
            )

            is StoryHomeScreen.Cart -> CartScreen(
                onBack = { router.pop() },
                onProductSelect = { router.push(Details(it)) }
            )
        }
    }
}
