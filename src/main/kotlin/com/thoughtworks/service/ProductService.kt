package com.thoughtworks.service

import com.thoughtworks.RetrofitService

suspend fun displayProducts() {
    val products = RetrofitService.api.getProducts()
    val inventories = RetrofitService.api.getInventories()

    val inventoryMap = inventories.groupBy { it.sku }.mapValues { entry ->
        entry.value.sumOf { it.stock }
    }

    products.forEach { product ->
        val stock = inventoryMap[product.sku] ?: 0
        val adjustedPrice = when {
            product.type == "NORMAL" -> product.price
            product.type == "HIGH_DEMAND" && stock > 100 -> product.price
            product.type == "HIGH_DEMAND" && stock in 31..100 -> product.price * 1.2
            product.type == "HIGH_DEMAND" && stock <= 30 -> product.price * 1.5
            else -> product.price
        }

        println("SKU: ${product.sku}, Name: ${product.name}, Price: $adjustedPrice, Stock: $stock, Image URL: ${product.imageUrl}")
    }
}
