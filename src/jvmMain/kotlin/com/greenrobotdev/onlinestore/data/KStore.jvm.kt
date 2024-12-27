package com.greenrobotdev.onlinestore.data

import com.greenrobotdev.onlinestore.data.model.CachedProducts
import com.greenrobotdev.onlinestore.data.model.CartProducts
import com.greenrobotdev.onlinestore.data.model.SavedProducts
import com.greenrobotdev.onlinestore.di.appStorage
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import okio.Path.Companion.toPath

actual val favoriteStore: KStore<SavedProducts> by lazy {
    storeOf("$appStorage/saved.json".toPath(), emptySet())
}

actual val cachedStore: KStore<CachedProducts> by lazy {
    storeOf("$appStorage/cached.json".toPath(), emptySet())
}

actual val cartStore: KStore<CartProducts> by lazy {
    storeOf("$appStorage/cart.json".toPath(), emptySet())
}