package com.example.shopiku.model

data class CartRequest(
    val productId: String,
    val quantity: Int = 1
)