package com.example.shopiku

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Memberikan jeda waktu 2 detik (2000 milidetik)
        Handler(Looper.getMainLooper()).postDelayed({

            // Perintah untuk pindah ke HomeActivity (Layar Beranda)
            // Anda bisa menggantinya ke LoginActivity::class.java nanti sesuai alur
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)

            // Menutup Splash Screen agar pengguna tidak bisa kembali ke layar ini
            // jika menekan tombol 'Back'
            finish()

        }, 2000)
    }
}