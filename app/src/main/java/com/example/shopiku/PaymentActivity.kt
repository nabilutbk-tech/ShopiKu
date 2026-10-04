package com.example.shopiku

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val tvTimer = findViewById<TextView>(R.id.tvTimer)
        val btnCheckPayment = findViewById<Button>(R.id.btnCheckPayment)

        // Waktu 24 jam dalam milidetik
        val timeInMillis = 24L * 60L * 60L * 1000L

        // Membuat Hitung Mundur
        object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24
                val minutes = (millisUntilFinished / (1000 * 60)) % 60
                val seconds = (millisUntilFinished / 1000) % 60

                val timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
                tvTimer.text = timeFormatted
            }

            override fun onFinish() {
                tvTimer.text = "00:00:00"
            }
        }.start()

        // Simulasi tombol cek pembayaran
        btnCheckPayment.setOnClickListener {
            Toast.makeText(this, "Pembayaran Berhasil Diverifikasi!", Toast.LENGTH_SHORT).show()

            // Kembali ke Beranda setelah sukses, dan hapus tumpukan layar sebelumnya
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}