package com.greenrobotdev.onlinestore.api

import com.greenrobotdev.core.utils.get
import com.greenrobotdev.onlinestore.data.model.ProductDTO
import io.ktor.client.HttpClient
import io.ktor.http.path

interface ProductListWebApi {
    suspend fun getProductListFromRemote(): Result<List<ProductDTO>>
}

class ProductListWebService(
    private val httpClient: HttpClient
) : ProductListWebApi {

    override suspend fun getProductListFromRemote(): Result<List<ProductDTO>> = httpClient
        .get { url { path("products") } }

}
