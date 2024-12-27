package com.greenrobotdev.onlinestore.screen.productList

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.greenrobotdev.onlinestore.domain.entity.Product

@Composable
actual fun ProductList(
    products: List<Product>,
    onRefresh: () -> Unit,
    isRefreshing : Boolean,
    onProductSelect: (product: Product) -> Unit,
    content: @Composable (product: Product) -> Unit,
){
        LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product -> content(product) }
    }
}
