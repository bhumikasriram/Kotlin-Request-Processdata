package com.thoughtworks.models

data class Product(
    val sku: String,
    val name: String,
    val price: Double,
    val type: String,  // NORMAL or HIGH_DEMAND
    val imageUrl: String
)