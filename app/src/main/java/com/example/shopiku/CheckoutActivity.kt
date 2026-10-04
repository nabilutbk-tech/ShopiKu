package com.example.shopiku

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {

    private var subtotal = 0.0
    private var shippingCost = 15000.0 // Default untuk Reguler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val tvFinalPrice = findViewById<TextView>(R.id.tvFinalPrice)
        val rgShipping = findViewById<RadioGroup>(R.id.rgShipping)
        val btnMakeOrder = findViewById<Button>(R.id.btnMakeOrder)
        // Catatan: pastikan id back button di activity_checkout.xml sesuai

        // Terima total belanja dari Keranjang
        subtotal = intent.getDoubleExtra("TOTAL_PRICE", 0.0)

        // Tampilkan total harga awal
        updateTotalPrice(tvFinalPrice)

        // Deteksi perubahan pilihan pengiriman
        rgShipping.setOnCheckedChangeListener { _, checkedId ->
            shippingCost = if (checkedId == R.id.rbExpress) {
                25000.0
            } else {
                15000.0
            }
            updateTotalPrice(tvFinalPrice)
        }

        // Tombol Buat Pesanan
        btnMakeOrder.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
            finish() // Tutup layar checkout
        }
    }

    private fun updateTotalPrice(tvFinalPrice: TextView) {
        val finalTotal = subtotal + shippingCost
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        tvFinalPrice.text = formatRupiah.format(finalTotal)
    }
}