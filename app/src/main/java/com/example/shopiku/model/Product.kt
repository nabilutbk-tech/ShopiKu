package com.example.shopiku.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Double,
    @SerializedName("rating") val rating: String
)