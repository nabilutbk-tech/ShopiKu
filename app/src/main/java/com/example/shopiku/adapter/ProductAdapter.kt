package com.example.shopiku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopiku.R
import com.example.shopiku.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduct: ImageView = view.findViewById(R.id.ivProductImage)
        val tvName: TextView = view.findViewById(R.id.tvProductName)
        val tvPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val tvRating: TextView = view.findViewById(R.id.tvProductRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.tvName.text = product.name
        holder.tvRating.text = product.rating

        // Format Harga ke Rupiah
        val localeID = java.util.Locale("id", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        holder.tvPrice.text = formatRupiah.format(product.price)

        // Load gambar dari URL menggunakan Glide
        Glide.with(holder.itemView.context)
            .load(product.image)
            .placeholder(android.R.drawable.ic_menu_gallery) // Gambar sementara saat loading
            .into(holder.imgProduct)
        holder.itemView.setOnClickListener {
            // Berpindah ke DetailActivity
            val intent = android.content.Intent(holder.itemView.context, com.example.shopiku.DetailActivity::class.java)
            // Mengirim data id buku ke layar detail agar bisa diambil info spesifiknya nanti
            intent.putExtra("PRODUCT_ID", product.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = products.size
}