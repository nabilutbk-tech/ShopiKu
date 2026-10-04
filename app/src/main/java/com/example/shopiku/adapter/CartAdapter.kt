package com.example.shopiku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopiku.R
import com.example.shopiku.model.CartItem
import java.text.NumberFormat

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onUpdateQuantity: (CartItem, Int) -> Unit,
    private val onDeleteItem: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCart: ImageView = view.findViewById(R.id.ivCartImage)
        val tvName: TextView = view.findViewById(R.id.tvCartName)
        val tvPrice: TextView = view.findViewById(R.id.tvCartPrice)
        val tvQuantity: TextView = view.findViewById(R.id.tvQuantityCart)
        val tvMinus: TextView = view.findViewById(R.id.tvMinusCart)
        val tvPlus: TextView = view.findViewById(R.id.tvPlusCart)
        val ivDelete: ImageView = view.findViewById(R.id.ivDeleteCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        val product = item.productDetails

        holder.tvQuantity.text = item.quantity.toString()

        if (product != null) {
            holder.tvName.text = product.name
            val formatRupiah = NumberFormat.getCurrencyInstance(java.util.Locale("id", "ID"))
            holder.tvPrice.text = formatRupiah.format(product.price * item.quantity) // Harga x Kuantitas

            Glide.with(holder.itemView.context)
                .load(product.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.imgCart)
        }

        // Aksi Tombol Kurang (-)
        holder.tvMinus.setOnClickListener {
            if (item.quantity > 1) {
                onUpdateQuantity(item, item.quantity - 1)
            }
        }

        // Aksi Tombol Tambah (+)
        holder.tvPlus.setOnClickListener {
            onUpdateQuantity(item, item.quantity + 1)
        }

        // Aksi Tombol Hapus (Tong Sampah)
        holder.ivDelete.setOnClickListener {
            onDeleteItem(item)
        }
    }

    override fun getItemCount() = cartItems.size

    fun updateData(newItems: List<CartItem>) {
        cartItems = newItems
        notifyDataSetChanged()
    }
}