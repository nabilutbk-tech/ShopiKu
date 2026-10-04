package com.example.shopiku.api

import com.example.shopiku.model.CartItem
import com.example.shopiku.model.CartRequest
import com.example.shopiku.model.Product
import retrofit2.Response
import retrofit2.http.*

interface ShopeeApiService {
    @GET("api/products")
    suspend fun getProducts(@Query("search") q: String? = null): Response<List<Product>>

    @POST("api/cart")
    suspend fun addToCart(@Body item: CartRequest): Response<Any>

    @GET("api/cart")
    suspend fun getCartItems(): Response<List<CartItem>>

    @PUT("api/cart/{id}")
    suspend fun updateCartQuantity(@Path("id") cartId: String, @Body body: Map<String, Int>): Response<Any>

    @DELETE("api/cart/{id}")
    suspend fun deleteCartItem(@Path("id") cartId: String): Response<Any>
}