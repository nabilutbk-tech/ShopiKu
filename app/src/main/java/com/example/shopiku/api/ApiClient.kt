package com.example.shopiku.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // PENTING: Ganti BASE_URL ini dengan URL dari project MockAPI Anda.
    // Contoh: "https://65XXXXX.mockapi.io/"
    // Pastikan diakhiri dengan tanda garis miring (slash) '/'
    private const val BASE_URL = "https://6ac1f6e43f4ae78f6944c7cf.mockapi.io/"

    val instance: ShopeeApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ShopeeApiService::class.java)
    }
}