package com.assignment.products.domain.wishproducts

import com.assignment.products.data.entity.Product
import com.assignment.products.di.DefaultDispatcher
import com.assignment.products.domain.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoveWishlistProductUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val wishListRepository: WishlistRepository,
) : SuspendUseCase<Product, Unit>(dispatcher) {
    override suspend fun execute(i: Product) {
        return wishListRepository.removeWishlistProduct(i)
    }
}