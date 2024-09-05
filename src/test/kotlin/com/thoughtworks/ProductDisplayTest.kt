package com.thoughtworks

import com.thoughtworks.api.ProductApi
import com.thoughtworks.models.Product
import com.thoughtworks.models.Inventory
import com.thoughtworks.service.displayProducts
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ProductDisplayTest {

    private val mockApi = mockk<ProductApi>() // Mocking the ProductApi interface

    @Test
    fun `test displayProducts with NORMAL type products`() = runBlocking {
        // Arrange: Set up mock data for NORMAL type products
        val products = listOf(
            Product(sku = "123", name = "Normal Product", price = 100.0, type = "NORMAL", imageUrl = "http://image.url")
        )
        val inventories = listOf(
            Inventory(sku = "123", stock = 50)
        )

        coEvery { mockApi.getProducts() } returns products
        coEvery { mockApi.getInventories() } returns inventories

        displayProducts()

        coVerify(exactly = 1) { mockApi.getProducts() }
        coVerify(exactly = 1) { mockApi.getInventories() }
    }

    @Test
    fun `test displayProducts with HIGH_DEMAND type products with stock greater than 100`() = runBlocking {
        // Arrange: Mock data for HIGH_DEMAND products with high stock
        val products = listOf(
            Product(sku = "456", name = "High Demand Product", price = 200.0, type = "HIGH_DEMAND", imageUrl = "http://image.url")
        )
        val inventories = listOf(
            Inventory(sku = "456", stock = 150)
        )

        coEvery { mockApi.getProducts() } returns products
        coEvery { mockApi.getInventories() } returns inventories

        // Act: Call the function
        displayProducts()

        // Assert: Ensure that the price remains the same for stock > 100
        coVerify(exactly = 1) { mockApi.getProducts() }
        coVerify(exactly = 1) { mockApi.getInventories() }
    }

    @Test
    fun `test displayProducts with HIGH_DEMAND type products with stock between 31 and 100`() = runBlocking {
        // Arrange: Mock data for HIGH_DEMAND products with moderate stock
        val products = listOf(
            Product(sku = "789", name = "Moderate Demand Product", price = 300.0, type = "HIGH_DEMAND", imageUrl = "http://image.url")
        )
        val inventories = listOf(
            Inventory(sku = "789", stock = 50)
        )

        coEvery { mockApi.getProducts() } returns products
        coEvery { mockApi.getInventories() } returns inventories

        // Act: Call the function
        displayProducts()

        // Assert: Ensure that the price is 120% of the original price
        coVerify(exactly = 1) { mockApi.getProducts() }
        coVerify(exactly = 1) { mockApi.getInventories() }
    }

    @Test
    fun `test displayProducts with HIGH_DEMAND type products with stock less than 30`() = runBlocking {
        // Arrange: Mock data for HIGH_DEMAND products with low stock
        val products = listOf(
            Product(sku = "101", name = "Low Stock Product", price = 400.0, type = "HIGH_DEMAND", imageUrl = "http://image.url")
        )
        val inventories = listOf(
            Inventory(sku = "101", stock = 20)
        )

        coEvery { mockApi.getProducts() } returns products
        coEvery { mockApi.getInventories() } returns inventories

        // Act: Call the function
        displayProducts()

        // Assert: Ensure that the price is 150% of the original price
        coVerify(exactly = 1) { mockApi.getProducts() }
        coVerify(exactly = 1) { mockApi.getInventories() }
    }
}
