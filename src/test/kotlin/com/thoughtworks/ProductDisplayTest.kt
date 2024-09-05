package com.thoughtworks

import com.thoughtworks.models.Inventory
import com.thoughtworks.models.Product
import com.thoughtworks.service.displayProducts
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


class ProductDisplayTest {

    @Test
    fun `should display products with adjusted prices`() = runBlocking {
        // Mock the RetrofitService API
        val retrofit = mockk<RetrofitService>()
        val mockProducts = listOf(
            Product(sku = "P1", name = "Product 1", price = 100.0, type = "NORMAL", imageUrl = "url1"),
            Product(sku = "P2", name = "Product 2", price = 200.0, type = "HIGH_DEMAND", imageUrl = "url2")
        )
        val mockInventories = listOf(
            Inventory(sku = "P1", stock = 50),
            Inventory(sku = "P2", stock = 20)
        )

        coEvery { retrofit.api.getProducts() } returns mockProducts
        coEvery { retrofit.api.getInventories() } returns mockInventories

        displayProducts()

        coVerify { RetrofitService.api.getProducts() }
        coVerify { RetrofitService.api.getInventories() }

    }
}
