package com.example.shopiku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopiku.api.ApiClient
import com.example.shopiku.model.Product
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchProducts(searchQuery: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Memanggil endpoint getProducts yang sudah kita buat
                val response = ApiClient.instance.getProducts(searchQuery)
                if (response.isSuccessful) {
                    _products.value = response.body()
                } else {
                    // Bisa ditangani jika terjadi error dari server
                }
            } catch (e: Exception) {
                // Menangani error koneksi (misal: internet mati)
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}