package com.greenrobotdev.onlinestore

import com.greenrobotdev.onlinestore.api.onlineStoreApiModule
import com.greenrobotdev.onlinestore.data.onlineStoreDataModule
import org.koin.core.module.Module

val onlineStoreModule: List<Module> = listOf(onlineStoreDataModule, onlineStoreApiModule)
