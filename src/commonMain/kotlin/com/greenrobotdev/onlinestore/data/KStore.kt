package com.greenrobotdev.onlinestore.data

import com.greenrobotdev.onlinestore.data.model.CachedProducts
import com.greenrobotdev.onlinestore.data.model.CartProducts
import com.greenrobotdev.onlinestore.data.model.SavedProducts
import io.github.xxfast.kstore.KStore

expect val favoriteStore: KStore<SavedProducts>
expect val cachedStore: KStore<CachedProducts>
expect val cartStore: KStore<CartProducts>
