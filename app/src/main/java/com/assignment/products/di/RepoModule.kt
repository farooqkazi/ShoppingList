package com.assignment.products.di

import com.assignment.products.data.repository.CartRepositoryImpl
import com.assignment.products.data.repository.ProductsRepositoryImpl
import com.assignment.products.data.repository.WishlistRepositoryImpl
import com.assignment.products.domain.cartproducts.CartRepository
import com.assignment.products.domain.products.ProductsRepository
import com.assignment.products.domain.wishproducts.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun provideProductsRepository(
        productsRepositoryImpl: ProductsRepositoryImpl
    ): ProductsRepository

    @Binds
    abstract fun provideCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

    @Binds
    abstract fun provideWishlistRepository(
        wishlistRepositoryImpl: WishlistRepositoryImpl
    ): WishlistRepository
}