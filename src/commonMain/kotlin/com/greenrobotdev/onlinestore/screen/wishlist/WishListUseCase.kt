package com.greenrobotdev.onlinestore.screen.wishlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.greenrobotdev.onlinestore.data.model.SavedProducts
import com.greenrobotdev.onlinestore.domain.entity.Product
import com.greenrobotdev.onlinestore.screen.productDetails.DontKnowYet
import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.map

@Composable
fun WishListUseCase(
    initialState: WishListState,
    store: KStore<SavedProducts>
): WishListState {
    val state by remember { mutableStateOf(initialState) }

    val products: List<Product>? by store.updates.map {
        if(it?.size == 0) nothing else it?.toList()
    }.collectAsState(DontKnowYet)

    return state.copy(products = products)
}