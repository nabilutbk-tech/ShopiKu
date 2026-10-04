package com.example.shopiku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopiku.api.ApiClient
import com.example.shopiku.model.CartItem
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun fetchCartItems() {
        viewModelScope.launch {
            try {
                // Mengambil data keranjang dan data produk secara bersamaan
                val cartResponse = ApiClient.instance.getCartItems()
                val productResponse = ApiClient.instance.getProducts()

                if (cartResponse.isSuccessful && productResponse.isSuccessful) {
                    val cartList = cartResponse.body() ?: emptyList()
                    val productList = productResponse.body() ?: emptyList()

                    var total = 0.0
                    // Cocokkan ID Produk di keranjang dengan ID Produk di katalog
                    for (item in cartList) {
                        val product = productList.find { it.id == item.productId }
                        item.productDetails = product
                        if (product != null) {
                            total += (product.price * item.quantity)
                        }
                    }
                    _cartItems.value = cartList
                    _totalPrice.value = total
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQuantity(cartId: String, newQuantity: Int) {
        viewModelScope.launch {
            try {
                val body = mapOf("quantity" to newQuantity)
                val response = ApiClient.instance.updateCartQuantity(cartId, body)
                if (response.isSuccessful) {
                    fetchCartItems() // Refresh data setelah berhasil update
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteItem(cartId: String) {
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.deleteCartItem(cartId)
                if (response.isSuccessful) {
                    fetchCartItems() // Refresh data setelah berhasil hapus
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}