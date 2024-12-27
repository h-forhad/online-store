package com.greenrobotdev.onlinestore.screen.wishlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.greenrobotdev.onlinestore.domain.entity.Product
import com.greenrobotdev.onlinestore.ui.EmptyView
import com.seiko.imageloader.rememberAsyncImagePainter
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun WishlistScreen(
    onBack: () -> Unit,
    onProductSelect: (product: Product) -> Unit
) {
    val viewModel: WishListViewModel =
        rememberOnRoute(WishListViewModel::class) { savedState ->
            WishListViewModel(savedState)
        }

    val state: WishListState by viewModel.states.collectAsState()

    WishlistView(
        state = state,
        onProductSelect = onProductSelect,
        onBack = onBack
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistView(
    state: WishListState,
    onProductSelect: (product: Product) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wishlist") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Rounded.ArrowBack, contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = Modifier
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {

            if (state.products != nothing) ProductList(
                products = state.products,
                onProductSelect = onProductSelect
            )

            AnimatedVisibility(
                visible = state.products == nothing,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    EmptyView("Your wishlist is empty",Icons.Filled.List)
                }
            }
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onProductSelect: (product: Product) -> Unit,
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) {
            ProductItem(
                item = it,
                onProductSelect = onProductSelect
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(
    item: Product,
    onProductSelect: (product: Product) -> Unit,
) {
    Card(
        onClick = { onProductSelect(item) },
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Image(
            painter = rememberAsyncImagePainter(item.image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(180.dp)
        )

        Column(
            modifier = Modifier.padding(6.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Light
            )

            val price = "Price: ${'$'}${item.price.toInt()}"

            Text(
                text = price,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
