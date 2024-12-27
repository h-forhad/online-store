package com.greenrobotdev.onlinestore.api

import com.greenrobotdev.onlinestore.api.ProductListWebApi
import com.greenrobotdev.onlinestore.api.ProductListWebService
import org.koin.dsl.module

internal val onlineStoreApiModule = module {
    single<ProductListWebApi> { ProductListWebService(httpClient = get()) }
}
