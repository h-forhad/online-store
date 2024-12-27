package com.greenrobotdev.onlinestore.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.greenrobotdev.onlinestore.domain.entity.Product
import com.seiko.imageloader.rememberAsyncImagePainter

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

/*
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


        Image(
            painter = rememberAsyncImagePainter(item.image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(180.dp)
                .align(Alignment.End)
        )

    }
}
 */

