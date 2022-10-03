package com.assignment.products.domain.cartproducts

import com.assignment.products.data.entity.Product
import com.assignment.products.di.DefaultDispatcher
import com.assignment.products.domain.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoadCartProductsUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val cartRepository: CartRepository,
) : SuspendUseCase<Unit, List<Product>>(dispatcher) {
    override suspend fun execute(i: Unit): List<Product> {
        return cartRepository.getCartProducts()
    }
}