package com.greenrobotdev.onlinestore.screen.productList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.greenrobotdev.onlinestore.domain.entity.Product

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun ProductList(
    products: List<Product>,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    onProductSelect: (product: Product) -> Unit,
    content: @Composable (product: Product) -> Unit,
) {

    val refreshing by remember { mutableStateOf(isRefreshing) }

    val pullState: PullRefreshState = rememberPullRefreshState(refreshing, onRefresh)

    Box(
        modifier = androidx.compose.ui.Modifier.pullRefresh(pullState)
    ) {

        if (!isRefreshing) LazyVerticalStaggeredGrid(
            state = rememberLazyStaggeredGridState(),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
            columns = StaggeredGridCells.Adaptive(160.dp),
        ) {
            items(products) { product -> content(product) }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullState,
            modifier = androidx.compose.ui.Modifier.align(Alignment.TopCenter)
        )
    }


}
