package com.example.shopiku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopiku.adapter.CartAdapter
import com.example.shopiku.viewmodel.CartViewModel
import java.text.NumberFormat
import java.util.Locale

class CartActivity : AppCompatActivity() {

    private lateinit var viewModel: CartViewModel
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val rvCartItems = findViewById<RecyclerView>(R.id.rvCartItems)
        val tvTotalPriceCart = findViewById<TextView>(R.id.tvTotalPriceCart)
        val btnCheckoutCart = findViewById<Button>(R.id.btnCheckoutCart)
        val ivBackCart = findViewById<ImageView>(R.id.ivBackCart)

        ivBackCart.setOnClickListener { finish() }

        // Siapkan RecyclerView dengan CartAdapter
        rvCartItems.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(emptyList(),
            onUpdateQuantity = { item, newQty ->
                viewModel.updateQuantity(item.id, newQty) // Panggil fungsi PUT API
            },
            onDeleteItem = { item ->
                viewModel.deleteItem(item.id) // Panggil fungsi DELETE API
            }
        )
        rvCartItems.adapter = adapter

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]

        // Pantau perubahan daftar keranjang
        viewModel.cartItems.observe(this) { items ->
            adapter.updateData(items)
            btnCheckoutCart.text = "Checkout (${items.size})"
        }

        // Pantau perubahan total harga (langsung diformat ke Rupiah)
        viewModel.totalPrice.observe(this) { total ->
            val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            tvTotalPriceCart.text = formatRupiah.format(total)
        }

        // Aksi Tombol Checkout
        btnCheckoutCart.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            // Kirim total harga ke layar Checkout
            intent.putExtra("TOTAL_PRICE", viewModel.totalPrice.value ?: 0.0)
            startActivity(intent)
        }

        // Ambil data keranjang saat layar pertama kali dibuka
        viewModel.fetchCartItems()
    }
}