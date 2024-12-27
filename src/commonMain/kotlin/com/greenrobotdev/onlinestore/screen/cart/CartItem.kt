package com.greenrobotdev.onlinestore.screen.cart

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.greenrobotdev.onlinestore.domain.entity.CartItem
import com.seiko.imageloader.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CartItem(
    item: CartItem,
    isChecked: Boolean,
    onSelectProduct: (isChecked: Boolean) -> Unit,
    onViewProductDetails: () -> Unit,
    addQuantity: () -> Unit,
    removeQuantity: () -> Unit,
    removeProduct: () -> Unit,

    ) {
    Card(
        onClick = { onViewProductDetails() },
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(8.dp).background(MaterialTheme.colorScheme.tertiaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = isChecked,
                onCheckedChange = { onSelectProduct(it) }
            )

            Image(
                painter = rememberAsyncImagePainter(item.product.image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .background(Color.White)
                    .size(80.dp)
            )

            Column(
                modifier = Modifier.padding(6.dp),
            ) {
                Text(
                    text = item.product.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Light
                )

                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    val price = "${'$'}${item.product.price.toInt()}"

                    Text(
                        text = price,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        modifier = Modifier.weight(0.5f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CartItemButton(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Filled.Remove,
                            onClick = { removeQuantity() },
                            enabled = item.quantity != 1
                        )
                        AnimatedContent(targetState = item.quantity) {
                            Text(
                                modifier = Modifier
                                    .defaultMinSize(30.dp)
                                    .align(Alignment.CenterVertically)
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                                text = item.quantity.toString()
                            )
                        }

                        CartItemButton(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Filled.Add,
                            onClick = { addQuantity() }
                        )
                    }

                    Button(
                        onClick = { removeProduct() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                        )
                    ) {
                        Text("Remove")
                    }
                }
            }
        }
    }
}

@Composable
internal fun CartItemButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        contentPadding = PaddingValues(2.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Icon(imageVector = imageVector, contentDescription = null)
    }
}
