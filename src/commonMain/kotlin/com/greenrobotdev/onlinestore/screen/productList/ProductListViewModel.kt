package com.greenrobotdev.onlinestore.screen.productList

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import com.greenrobotdev.onlinestore.api.ProductListWebApi
import com.greenrobotdev.onlinestore.data.cartStore
import com.greenrobotdev.onlinestore.data.favoriteStore
import com.greenrobotdev.core.screen.ViewModel
import io.github.xxfast.decompose.router.RouterContext
import io.github.xxfast.decompose.router.state
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductListViewModel(
    context: RouterContext,
) : ViewModel(), KoinComponent {
    private val initialState: ProductListState = context.state(ProductListState()) {
        states.value
    }
    private val webService: ProductListWebApi by inject()

    private val eventsFlow: MutableSharedFlow<ProductListEvent> = MutableSharedFlow(5)

    val states by lazy {
        moleculeFlow(RecompositionMode.Immediate) {
            ProductListUseCase(initialState, webService, favoriteStore, cartStore, eventsFlow)
        }
            .stateIn(this, SharingStarted.Lazily, initialState)
    }

    fun onRefresh() {
        launch { eventsFlow.emit(ProductListEvent.Refresh) }
    }

    fun onUpdate(string: String){
        launch { eventsFlow.emit(ProductListEvent.Update(string)) }
    }
}
