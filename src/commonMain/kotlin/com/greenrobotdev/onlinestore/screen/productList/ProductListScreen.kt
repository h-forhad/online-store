package com.greenrobotdev.onlinestore.screen.productList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.greenrobotdev.core.ui.TopAppBar
import com.greenrobotdev.core.utils.statusBarPadding
import com.greenrobotdev.onlinestore.domain.entity.Product
import com.greenrobotdev.onlinestore.ui.ProductItem
import composemultiplatformtemplate.online_store.generated.resources.Res
import composemultiplatformtemplate.online_store.generated.resources.allStringResources
import composemultiplatformtemplate.online_store.generated.resources.app_name
import composemultiplatformtemplate.online_store.generated.resources.cart_button
import composemultiplatformtemplate.online_store.generated.resources.favorite_button
import composemultiplatformtemplate.online_store.generated.resources.ic_launcher
import composemultiplatformtemplate.online_store.generated.resources.ic_test
import composemultiplatformtemplate.online_store.generated.resources.retry_button
import composemultiplatformtemplate.online_store.generated.resources.something_wrong
import io.github.xxfast.decompose.router.rememberOnRoute
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProductListScreen(
    onProductSelect: (product: Product) -> Unit,
    wishlistSelect: () -> Unit,
    cartSelected: () -> Unit,
) {

    val viewModel: ProductListViewModel =
        rememberOnRoute(ProductListViewModel::class) { savedState ->
            ProductListViewModel(savedState)
        }

    val state: ProductListState by viewModel.states.collectAsState()
    ProductListView(
        state = state,
        onProductSelect = onProductSelect,
        onRefresh = { viewModel.onRefresh() },
        onWishListPressed = wishlistSelect,
        onCartPressed = cartSelected,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListView(
    state: ProductListState,
    onProductSelect: (product: Product) -> Unit,
    onRefresh: () -> Unit,
    onWishListPressed: () -> Unit,
    onCartPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(Res.drawable.ic_launcher),
                            contentDescription = stringResource(Res.string.app_name)
                        )
                        Text(
                            text = stringResource(Res.string.app_name),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                },
                actions = {
                    Row(modifier = Modifier.wrapContentWidth()) {
                        IconButton(onClick = onWishListPressed) {
                            BadgedBox(
                                badge = {
                                    if (state.numberOfFavorite > 0)
                                        Badge {
                                            Text(state.numberOfFavorite.toString())
                                        }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = stringResource(Res.string.favorite_button)
                                )
                            }
                        }
                        IconButton(onClick = onCartPressed) {
                            BadgedBox(
                                badge = {
                                    if (state.numberOfCartProducts > 0) {
                                        Badge {
                                            Text(state.numberOfCartProducts.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ShoppingCart,
                                    contentDescription = stringResource(Res.string.cart_button)
                                )
                            }
                        }
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

            when {
                state.hasError == true -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(Res.string.something_wrong))

                        Button(onClick = onRefresh) {
                            Text(stringResource(Res.string.retry_button))
                        }
                    }
                }

                state.products == Loading -> Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.products != Loading -> ProductList(
                    products = state.products,
                    onRefresh = onRefresh,
                    isRefreshing = state.isRefreshing,
                    onProductSelect = onProductSelect
                ) {
                    ProductItem(
                        item = it,
                        onProductSelect = onProductSelect
                    )
                }
            }
        }
    }
}

