package com.greenrobotdev.onlinestore.screen.productList

import androidx.compose.runtime.Composable
import com.greenrobotdev.onlinestore.domain.entity.Product

@Composable
expect fun ProductList(
    products: List<Product>,
    onRefresh: () -> Unit,
    isRefreshing : Boolean,
    onProductSelect: (product: Product) -> Unit,
    content: @Composable (product: Product) -> Unit,
)