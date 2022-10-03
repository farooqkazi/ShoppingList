package com.assignment.products

import com.assignment.products.domain.products.LoadAllProductsUseCase
import com.assignment.products.domain.wishproducts.AddWishlistProductUseCase
import com.assignment.products.domain.wishproducts.RemoveWishlistProductUseCase
import com.assignment.products.presentation.productlist.ProductListViewModel
import com.assignment.products.presentation.productlist.ProductListViewModel.UiState
import com.assignment.products.rules.CoroutineDispatcherRule
import com.assignment.products.testdoubles.ScenarioType
import com.assignment.products.testdoubles.getProducts
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    @get:Rule
    val coroutineDispatcherRule = CoroutineDispatcherRule()

    private val loadAllProductsUseCase = mockk<LoadAllProductsUseCase>()
    private val addWishlistProductUseCase = mockk<AddWishlistProductUseCase>()
    private val removeWishlistProductUseCase = mockk<RemoveWishlistProductUseCase>()
    private lateinit var viewModel: ProductListViewModel

    @Before
    fun setup() {
        viewModel = ProductListViewModel(
            loadAllProductsUseCase,
            addWishlistProductUseCase,
            removeWishlistProductUseCase
        )
    }

    @Test
    fun `When data is empty`() = runTest {
        coEvery { loadAllProductsUseCase(Unit) } returns Result.success(getProducts(ScenarioType.EMPTY))

        viewModel.loadData()
        advanceUntilIdle()

        val value = viewModel.uiState.value as UiState.Success
        assertTrue(value.products.isEmpty())
    }

    @Test
    fun `When data loads with no duplicates, verify the data`() = runTest {
        coEvery { loadAllProductsUseCase(Unit) } returns Result.success(getProducts())

        viewModel.loadData()
        advanceUntilIdle()

        val value = viewModel.uiState.value as UiState.Success
        assertEquals(3, value.products.size)
    }

    @Test
    fun `When data loads with duplicates, verify the data`() = runTest {
        val data = getProducts(ScenarioType.DUPLICATES)
        coEvery { loadAllProductsUseCase(Unit) } returns Result.success(data)

        viewModel.loadData()
        advanceUntilIdle()

        val value = viewModel.uiState.value as UiState.Success
        assertEquals(4, value.products.size)
        assertEquals(data[0].name, value.products[0].name)
        assertEquals(data[3].name, value.products[3].name)
    }

    @Test
    fun `When data loads as unsorted list, verify the sorted list`() = runTest {
        val data = getProducts(ScenarioType.UNSORTED)
        coEvery { loadAllProductsUseCase(Unit) } returns Result.success(data)

        viewModel.loadData()
        advanceUntilIdle()

        val value = viewModel.uiState.value as UiState.Success
        assertEquals(3, value.products.size)
        assertEquals(data[0].name, value.products[2].name)
        assertEquals(data[1].name, value.products[1].name)
        assertEquals(data[2].name, value.products[0].name)
    }

    @Test
    fun `When repo fails with unknown reason, then handle and show unknown error message`() =
        runTest {
            val exception = Exception()
            coEvery { loadAllProductsUseCase(Unit) } returns Result.failure(exception)

            viewModel.loadData()
            advanceUntilIdle()

            val value = viewModel.uiState.value as UiState.Error
            assertEquals(DEFAULT_ERROR_MSG, value.msg)
        }

    @Test
    fun `When repo fails with custom error, then show that error message`() = runTest {
        coEvery { loadAllProductsUseCase(Unit) } returns Result.failure(Throwable(ERROR_MSG))

        viewModel.loadData()
        advanceUntilIdle()

        val value = viewModel.uiState.value as UiState.Error
        assertEquals(ERROR_MSG, value.msg)
    }

    private companion object {
        const val DEFAULT_ERROR_MSG = "Unknown Error"
        const val ERROR_MSG = "Error"
    }
}
