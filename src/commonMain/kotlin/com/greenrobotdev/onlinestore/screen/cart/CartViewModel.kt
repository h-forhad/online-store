package com.greenrobotdev.onlinestore.screen.cart

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import com.greenrobotdev.core.screen.ViewModel
import com.greenrobotdev.onlinestore.data.cartStore
import com.greenrobotdev.onlinestore.domain.entity.CartItem
import io.github.xxfast.decompose.router.RouterContext
import io.github.xxfast.decompose.router.state
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class CartViewModel(
  context: RouterContext,
) : ViewModel(), KoinComponent {
  private val initialState: CartState = context.state(CartState()) {
    states.value
  }
  private val eventsFlow: MutableSharedFlow<CartEvent> = MutableSharedFlow(5)

  val states by lazy {
    moleculeFlow(RecompositionMode.Immediate) {
      CartUseCase(initialState, cartStore, eventsFlow)
    }.stateIn(this, SharingStarted.Lazily, initialState)
  }

  fun onRemoveProduct(cartItem: CartItem){
    launch { eventsFlow.emit(CartEvent.OnRemove(cartItem)) }
  }

  fun onSelectProduct(cartItem: CartItem, isSelected : Boolean){
    launch { eventsFlow.emit(CartEvent.OnSelect(cartItem,isSelected)) }
  }

  fun onAddQuantity(cartItem: CartItem){
    launch { eventsFlow.emit(CartEvent.OnIncreaseQt(cartItem)) }
  }

  fun onDecreaseQuantity(cartItem: CartItem){
    launch { eventsFlow.emit(CartEvent.OnDecreaseQt(cartItem)) }
  }

}
