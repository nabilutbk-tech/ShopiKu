package com.example.shopiku

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopiku.adapter.ProductAdapter
import com.example.shopiku.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var rvProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rvProducts = findViewById(R.id.rvProducts)

        // Mengatur tampilan menjadi Grid 2 Kolom sesuai panduan
        rvProducts.layoutManager = GridLayoutManager(this, 2)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Memantau perubahan data produk
        viewModel.products.observe(this) { productList ->
            if (productList != null) {
                val adapter = ProductAdapter(productList)
                rvProducts.adapter = adapter
            }
        }

        // Opsional: Memantau status loading
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                // Tampilkan pesan atau progress bar
                Toast.makeText(this, "Memuat data...", Toast.LENGTH_SHORT).show()
            }
        }

        // Panggil fungsi untuk mengambil data dari MockAPI
        viewModel.fetchProducts()
    }
}