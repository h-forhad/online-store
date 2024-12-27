package com.greenrobotdev.onlinestore.screen.productDetails

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.greenrobotdev.core.utils.navigationBarPadding
import com.greenrobotdev.onlinestore.domain.entity.Product
import com.seiko.imageloader.rememberAsyncImagePainter
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun ProductDetailsScreen(
    product: Product,
    onBack: () -> Unit,
) {

    val viewModel: ProductDetailsViewModel =
        rememberOnRoute(ProductDetailsViewModel::class) { savedState ->
            ProductDetailsViewModel(savedState, product)
        }

    val state: ProductDetailsState by viewModel.states.collectAsState()

    ProductDetailsView(
        onBack = onBack,
        state = state,
        onFavoritePressed = { viewModel.onFavoriteButtonPressed() },
        onAddToCartPressed = { viewModel.onAddToCartPressed() },
        onDecreaseCountPressed = { viewModel.onDecreaseCountPressed() },
        onIncreaseCountPressed = { viewModel.onIncreaseCountPressed() }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsView(
    state: ProductDetailsState,
    onBack: () -> Unit,
    onFavoritePressed: () -> Unit,
    onAddToCartPressed: () -> Unit,
    onDecreaseCountPressed: () -> Unit,
    onIncreaseCountPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            com.greenrobotdev.core.ui.TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Rounded.ArrowBack, contentDescription = null
                        )
                    }
                },
            )

        },
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBarPadding),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                CartButton(
                    numberOfProductInCart = state.numberOfProduct,
                    onAddToCartPressed = onAddToCartPressed,
                    onDecreaseCountPressed = onDecreaseCountPressed,
                    onIncreaseCountPressed = onIncreaseCountPressed
                )
            }
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier.padding(scaffoldPadding).wrapContentSize()
        ) {
            if (state.product != null) ProductDetails(
                product = state.product,
                onFavoritePressed = onFavoritePressed,
                isSaved = state.isSaved
            )
        }
    }
}

@Composable
fun ProductDetails(
    product: Product,
    onFavoritePressed: () -> Unit,
    isSaved: Boolean
) {
    Column {
        Image(
            painter = rememberAsyncImagePainter(product.image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .clip(CircleShape)
                .padding(14.dp)
                .size(180.dp)
        )

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                val price = "${'$'}${product.price.toInt()}"
                Text(
                    text = price,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = onFavoritePressed,
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = null
                    )
                }

            }

            Text(
                text = product.title,
                style = MaterialTheme.typography.titleLarge,
            )

            Card(
                shape = MaterialTheme.shapes.extraSmall,
                modifier = Modifier.padding(2.dp).wrapContentSize(),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(6.dp)
                )
            }

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CartButton(
    numberOfProductInCart: Int,
    onAddToCartPressed: () -> Unit,
    onIncreaseCountPressed: () -> Unit,
    onDecreaseCountPressed: () -> Unit,
) {
    AnimatedVisibility(
        visible = numberOfProductInCart == 0,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onAddToCartPressed,
            shape = RoundedCornerShape(30.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.ShoppingCart,
                contentDescription = null
            )
            Text("Add to cart")
        }
    }

    AnimatedVisibility(
        visible = numberOfProductInCart != 0,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.weight(.5f).padding(4.dp),
                onClick = onDecreaseCountPressed,
                shape = RoundedCornerShape(30.dp)
            ) {
                Icon(imageVector = Icons.Filled.Remove, contentDescription = null)
            }

            Spacer(modifier = Modifier.padding(start = 10.dp))
            BadgedBox(
                badge = {
                    Badge(
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        AnimatedContent(targetState = numberOfProductInCart) {
                            Text(numberOfProductInCart.toString())
                        }

                    }
                }
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .padding(8.dp),
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Cart"
                )
            }
            Spacer(modifier = Modifier.padding(start = 10.dp))

            Button(
                modifier = Modifier.weight(.5f).padding(4.dp),
                onClick = onIncreaseCountPressed,
                shape = RoundedCornerShape(30.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    }

}

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}