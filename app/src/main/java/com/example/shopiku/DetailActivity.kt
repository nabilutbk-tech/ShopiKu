package com.example.shopiku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.shopiku.api.ApiClient
import com.example.shopiku.model.CartRequest
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val btnAddToCart = findViewById<Button>(R.id.btnAddToCart)
        val ivBack = findViewById<ImageView>(R.id.ivBack)

        // Tombol kembali ke Beranda
        ivBack.setOnClickListener {
            finish()
        }

        // Mengambil ID Buku yang dikirim dari HomeActivity tadi
        val productId = intent.getStringExtra("PRODUCT_ID") ?: "1"

        // Aksi ketika tombol Tambah ke Keranjang ditekan
        btnAddToCart.setOnClickListener {
            btnAddToCart.isEnabled = false // Matikan sementara agar tidak di-klik 2 kali

            // Mengirim request API di background
            lifecycleScope.launch {
                try {
                    val request = CartRequest(productId = productId, quantity = 1)
                    val response = ApiClient.instance.addToCart(request)

                    if (response.isSuccessful) {
                        Toast.makeText(this@DetailActivity, "Berhasil masuk keranjang!", Toast.LENGTH_SHORT).show()

                        // Pindah ke layar Keranjang Belanja
                        val intentCart = Intent(this@DetailActivity, CartActivity::class.java)
                        startActivity(intentCart)
                    } else {
                        Toast.makeText(this@DetailActivity, "Gagal menambah ke keranjang", Toast.LENGTH_SHORT).show()
                        btnAddToCart.isEnabled = true
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, "Error koneksi", Toast.LENGTH_SHORT).show()
                    btnAddToCart.isEnabled = true
                }
            }
        }
    }
}