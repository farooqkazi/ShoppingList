package com.assignment.products.domain.wishproducts

import com.assignment.products.data.entity.Product
import com.assignment.products.di.DefaultDispatcher
import com.assignment.products.domain.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoadWishlistProductsUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val wishListRepository: WishlistRepository,
) : SuspendUseCase<Unit, List<Product>>(dispatcher) {
    override suspend fun execute(i: Unit): List<Product> {
        return wishListRepository.getWishlistProducts()
    }
}