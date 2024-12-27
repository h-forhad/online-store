package com.greenrobotdev.onlinestore.screen.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.greenrobotdev.onlinestore.data.model.CartProducts
import com.greenrobotdev.onlinestore.domain.entity.CartItem
import com.greenrobotdev.onlinestore.screen.productDetails.DontKnowYet
import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun CartUseCase(
    initialState: CartState,
    cartStore: KStore<CartProducts>,
    events: Flow<CartEvent>,
): CartState {

    var selectedCartItems: List<CartItem> by remember { mutableStateOf(initialState.selectedCarts) }

    val cartItems: List<CartItem>? by cartStore.updates.map {
        if (it?.size == 0) nothing else it?.toList()
    }.collectAsState(DontKnowYet)

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is CartEvent.OnRemove -> launch(Dispatchers.Unconfined) {
                    val currentCartItem: CartItem = event.cartItem
                    cartStore.update { cartItems -> cartItems?.minus(currentCartItem) }
                    selectedCartItems = selectedCartItems.minus(currentCartItem)
                }

                is CartEvent.OnIncreaseQt -> launch(Dispatchers.Unconfined) {
                    val selectedCartItem: CartItem = event.cartItem
                    cartStore.update { cartItems ->
                        cartItems?.map { item ->
                            if (item.product.id == selectedCartItem.product.id) {
                                return@map item.copy(quantity = item.quantity.plus(1))
                            }
                            return@map item
                        }?.toSet()
                    }
                }

                is CartEvent.OnDecreaseQt -> launch(Dispatchers.Unconfined) {
                    val selectedCartItem: CartItem = event.cartItem
                    cartStore.update { cartItems ->
                        cartItems?.map { item ->
                            if (item.product.id == selectedCartItem.product.id) {
                                return@map item.copy(quantity = item.quantity.minus(1))
                            }
                            return@map item
                        }?.toSet()
                    }
                }

                is CartEvent.OnSelect -> {
                    selectedCartItems = if (event.isSelected) selectedCartItems.plus(event.cartItem)
                    else selectedCartItems.minus(event.cartItem)
                }
            }
        }
    }

    return CartState(cartItems = cartItems, selectedCarts = selectedCartItems)
}