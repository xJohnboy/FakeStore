package com.example.fakestore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.data.CartData
import kotlinx.android.synthetic.main.recycler_cart.view.*

class CartAdapter : RecyclerView.Adapter<CartAdapter.VHolder>() {
    private val data = ArrayList<CartData>()

    fun addItem(append: ArrayList<CartData>) {
        data.addAll(append)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_cart, parent, false)
        return VHolder(v)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class VHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bindItem(data: CartData) {
            itemView.cTitleText.text = data.title
            itemView.cPriceText.text = data.price.toString()
        }
    }
}