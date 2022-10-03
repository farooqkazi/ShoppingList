package com.assignment.products.data.api

import com.assignment.products.data.entity.ProductResponse
import retrofit2.http.GET

interface ProductsApi {

    @GET("v3/5c138271-d8dd-4112-8fb4-3adb1b7f689e")
    suspend fun getAllProducts(): ProductResponse

}