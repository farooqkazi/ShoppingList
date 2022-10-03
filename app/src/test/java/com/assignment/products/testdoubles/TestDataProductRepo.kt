package com.assignment.products.testdoubles

import com.assignment.products.data.entity.Product
import com.assignment.products.data.entity.ProductResponse
import com.assignment.products.domain.products.ProductsRepository

enum class ScenarioType {
    DEFAULT,
    EMPTY,
    DUPLICATES,
    UNSORTED
}

fun getFakeProductsRepo(types: ScenarioType = ScenarioType.DEFAULT): ProductsRepository {
    return when (types) {
        ScenarioType.DUPLICATES -> DuplicateFakeProductsRepository()
        ScenarioType.EMPTY -> EmptyFakeProductsRepository()
        else -> FakeProductsRepository()
    }
}

fun getProducts(types: ScenarioType = ScenarioType.DEFAULT): List<Product> {
    return when (types) {
        ScenarioType.EMPTY -> emptyList()
        ScenarioType.DUPLICATES -> listOf(
            product1,
            product2,
            product3,
            product4,
            product1,
            product4
        )
        ScenarioType.UNSORTED -> listOf(
            product3,
            product2,
            product1
        )
        else -> listOf(
            product1,
            product2,
            product3
        )
    }
}

class EmptyFakeProductsRepository : ProductsRepository {
    override suspend fun getAllProducts() = getProducts(ScenarioType.EMPTY)
}

class FakeProductsRepository : ProductsRepository {
    override suspend fun getAllProducts() = getProducts(ScenarioType.DEFAULT)
}

class DuplicateFakeProductsRepository : ProductsRepository {
    override suspend fun getAllProducts() = getProducts(ScenarioType.DUPLICATES)
}

val product1 = Product(
    "1",
    "534968GKQN61070",
    "https://i.imgur.com/4uck7eem.jpg",
    "SAINT LAURENT",
    "City backpack",
    4700,
    6000,
    emptyList()
)

val product2 = Product(
    "2",
    "02B225-746563",
    "https://i.imgur.com/Mmgh6OPm.jpg",
    "SALVATORE FERRAGAMO",
    "Odsy-2000 sneakers",
    3430,
    0,
    listOf("NEW")
)

val product3 = Product(
    "3",
    "JX02N-DEBKW",
    "https://i.imgur.com/EqsEOcxm.jpg",
    "PIERRE HARDY",
    "Rainbow Gancini sneakers",
    2230,
    2400,
    listOf("SALE")
)

val product4 = Product(
    "4",
    "OMIA190F21FAB001-1007",
    "https://i.imgur.com/oNo46c2m.jpg",
    "OFF-WHITE",
    "Slider Sneakers",
    2580,
    3000,
    listOf("NEW","SALE")
)
