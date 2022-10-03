package com.assignment.products.domain.cartproducts

import com.assignment.products.data.entity.Product
import com.assignment.products.di.DefaultDispatcher
import com.assignment.products.domain.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AddCartProductUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val cartRepository: CartRepository,
) : SuspendUseCase<Product, Unit>(dispatcher) {
    override suspend fun execute(i: Product) {
        return cartRepository.addProductToCart(i)
    }
}