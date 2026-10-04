package com.example.shopiku.model

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("id") val id: String,
    @SerializedName("productId") val productId: String,
    @SerializedName("quantity") var quantity: Int,
    @Transient var productDetails: Product? = null
)