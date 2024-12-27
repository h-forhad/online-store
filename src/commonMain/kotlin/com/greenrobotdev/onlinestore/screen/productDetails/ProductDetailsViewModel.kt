package com.greenrobotdev.onlinestore.screen.productDetails

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import com.greenrobotdev.onlinestore.data.cartStore
import com.greenrobotdev.onlinestore.data.favoriteStore
import com.greenrobotdev.onlinestore.domain.entity.Product
import com.greenrobotdev.core.screen.ViewModel
import io.github.xxfast.decompose.router.RouterContext
import io.github.xxfast.decompose.router.state
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ProductDetailsViewModel(
  context: RouterContext,
  product : Product? = null
) : ViewModel(), KoinComponent {
  private val eventsFlow: MutableSharedFlow<ProductDetailsEvent> = MutableSharedFlow(5)
  private val initialState: ProductDetailsState = context.state(ProductDetailsState(product)){
    states.value
  }

  val states by lazy {
    moleculeFlow(RecompositionMode.Immediate) {
      ProductDetailsUseCase(initialState, favoriteStore, cartStore,eventsFlow)
    }
      .stateIn(this, SharingStarted.Lazily, initialState)
  }

  fun onFavoriteButtonPressed(){
    launch { eventsFlow.emit(ProductDetailsEvent.OnFavoritePressed) }
  }

  fun onAddToCartPressed(){
    launch { eventsFlow.emit(ProductDetailsEvent.OnAddToCartPressed) }
  }

  fun onIncreaseCountPressed(){
    launch { eventsFlow.emit(ProductDetailsEvent.OnIncreaseCountPressed) }
  }
  fun onDecreaseCountPressed(){
    launch { eventsFlow.emit(ProductDetailsEvent.OnDecreaseCountPressed) }
  }

}
