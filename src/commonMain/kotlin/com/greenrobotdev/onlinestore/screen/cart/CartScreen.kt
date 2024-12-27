package com.greenrobotdev.onlinestore.screen.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.greenrobotdev.core.utils.navigationBarPadding
import com.greenrobotdev.onlinestore.domain.entity.CartItem
import com.greenrobotdev.onlinestore.domain.entity.Product
import com.greenrobotdev.onlinestore.ui.EmptyView
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun CartScreen(
    onBack: () -> Unit,
    onProductSelect: (product: Product) -> Unit
) {
    val viewModel: CartViewModel = rememberOnRoute(CartViewModel::class) { savedState ->
            CartViewModel(savedState)
        }

    val state: CartState by viewModel.states.collectAsState()

    CartView(
        state = state,
        onViewProductDetails = onProductSelect,
        onBack = onBack,
        addQuantity = { viewModel.onAddQuantity(it) },
        removeQuantity = { viewModel.onDecreaseQuantity(it) },
        removeProduct = { viewModel.onRemoveProduct(it) },
        onSelectProduct = { cartItem, isSelected ->
            viewModel.onSelectProduct(cartItem, isSelected)
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartView(
    state: CartState,
    onViewProductDetails: (product: Product) -> Unit,
    onBack: () -> Unit,
    onSelectProduct: (cartItem: CartItem, isSelected: Boolean) -> Unit,
    addQuantity: (cartItem: CartItem) -> Unit,
    removeQuantity: (cartItem: CartItem) -> Unit,
    removeProduct: (cartItem: CartItem) -> Unit,
) {

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { Text("Shopping cart") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Rounded.ArrowBack, contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (!state.cartItems.isNullOrEmpty()) BottomAppBar(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBarPadding),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                CartProceedView(
                    numberOfSelectedProduct = state.selectedCarts.size,
                    onProceedPressed = {}
                )
            }
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {

            if (state.cartItems != nothing) CartProductList(
                cartItems = state.cartItems,
                selectedCartItems = state.selectedCarts,
                onSelectProduct = onSelectProduct,
                onViewProductDetails = onViewProductDetails,
                addQuantity = addQuantity,
                removeQuantity = removeQuantity,
                removeProduct = removeProduct,
            )

            AnimatedVisibility(
                visible = state.cartItems == nothing,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    EmptyView("You cart is empty", Icons.Filled.ShoppingCartCheckout)
                }
            }
        }
    }
}

@Composable
fun CartProductList(
    cartItems: List<CartItem>,
    selectedCartItems: List<CartItem>,
    onSelectProduct: (cartItem: CartItem, isSelected: Boolean) -> Unit,
    onViewProductDetails: (product: Product) -> Unit,
    addQuantity: (cartItem: CartItem) -> Unit,
    removeQuantity: (cartItem: CartItem) -> Unit,
    removeProduct: (cartItem: CartItem) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {

        items(cartItems) { cartItem ->
            CartItem(
                item = cartItem,
                isChecked = selectedCartItems.contains(cartItem),
                onSelectProduct = { onSelectProduct(cartItem, it) },
                onViewProductDetails = { onViewProductDetails(cartItem.product) },
                addQuantity = { addQuantity(cartItem) },
                removeQuantity = { removeQuantity(cartItem) },
                removeProduct = { removeProduct(cartItem) }
            )
        }
    }
}

@Composable
fun CartProceedView(
    numberOfSelectedProduct: Int,
    onProceedPressed: () -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$numberOfSelectedProduct item selected")

        Button(
            modifier = Modifier.padding(4.dp),
            onClick = onProceedPressed,
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Proceed")
        }
    }

}