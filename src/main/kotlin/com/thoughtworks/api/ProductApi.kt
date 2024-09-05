package com.thoughtworks.api

import com.thoughtworks.models.Product
import com.thoughtworks.models.Inventory
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("inventories")
    suspend fun getInventories(): List<Inventory>
}
