package com.example.fakestore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.CartAdapter
import com.example.fakestore.R
import com.example.fakestore.data.CartData
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment() {
    private var adapter = CartAdapter()
    private var items = ArrayList<CartData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.addItem(items)
        val itemDecor = DividerItemDecoration(activity,DividerItemDecoration.VERTICAL)
        recyclerView2.addItemDecoration(itemDecor)
        recyclerView2.layoutManager = LinearLayoutManager(activity)
        recyclerView2.adapter = adapter
    }
}