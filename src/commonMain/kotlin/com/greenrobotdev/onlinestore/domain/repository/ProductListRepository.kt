package com.greenrobotdev.onlinestore.domain.repository

import com.greenrobotdev.onlinestore.data.base.Response
import com.greenrobotdev.onlinestore.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductListRepository {
    suspend fun getProductList(): Flow<Response<List<Product>>>
}