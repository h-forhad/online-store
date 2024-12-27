package com.greenrobotdev.onlinestore.screen.wishlist

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import com.greenrobotdev.onlinestore.data.favoriteStore
import com.greenrobotdev.core.screen.ViewModel
import io.github.xxfast.decompose.router.RouterContext
import io.github.xxfast.decompose.router.state
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent

class WishListViewModel(
  routerContext: RouterContext
) : ViewModel(), KoinComponent {
  private val initialState: WishListState = routerContext.state(WishListState()){
    states.value
  }

  val states by lazy {
    moleculeFlow(RecompositionMode.Immediate) {
       WishListUseCase(initialState, favoriteStore)
    }
      .stateIn(this, SharingStarted.Lazily, initialState)
  }

}
