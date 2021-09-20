package com.example.fakestore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.data.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_item.view.*

class Adapter : RecyclerView.Adapter<Adapter.VHolder>() {
    private val data = ArrayList<Data>()
    var onImageClick: ((Data) -> Unit)? = null

    fun addItem(append: ArrayList<Data>) {
        data.addAll(append)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return VHolder(v)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bindItem(data[position])
        Picasso.get().load(data[position].image).into(holder.imageView)
    }

    override fun getItemCount(): Int = data.size

    inner class VHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imageView = v.imageView
        fun bindItem(data: Data) {
            itemView.titleText.text = data.title
            itemView.priceText.text = data.price.toString()
        }
        init {
            imageView.setOnClickListener {
                onImageClick?.invoke(data[adapterPosition])
            }
        }
    }
}