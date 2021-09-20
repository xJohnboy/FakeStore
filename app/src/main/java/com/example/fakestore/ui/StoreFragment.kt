package com.example.fakestore.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.Adapter
import com.example.fakestore.CartAdapter
import com.example.fakestore.R
import com.example.fakestore.data.CartData
import com.example.fakestore.data.Data
import com.example.fakestore.data.api.Apiservice
import kotlinx.android.synthetic.main.fragment_store.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreFragment : Fragment() {
    lateinit var apiService: Apiservice
    private val adapter = Adapter()
    private var page = 0
    private var limit = 5
    private var isLoading = false
    private var items = ArrayList<CartData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh!!.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                swipeRefresh?.isRefreshing = true
                adapter.clear()
                page = 0
                fetchItem()
            }, 1000)
        }
        fetchItem()
        progressBar?.visibility = View.GONE
    }

    private fun fetchItem() {
        apiService = Apiservice()
        val call: Call<ArrayList<Data>> = apiService.getItem()
        call.enqueue(object : Callback<ArrayList<Data>> {
            override fun onResponse(
                call: Call<ArrayList<Data>>, response: Response<ArrayList<Data>>
            ) {
                if (response.isSuccessful)
                    swipeRefresh?.isRefreshing = false
                showItem(response.body()!!)
            }

            override fun onFailure(call: Call<ArrayList<Data>>, t: Throwable) {
                swipeRefresh?.isRefreshing = false
                Log.e("API", t.message.toString())
            }
        })
    }

    private fun showItem(item: ArrayList<Data>) {
        val itemDecor = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView?.addItemDecoration(itemDecor)
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (page < 3)
                    if (dy > 0)
                        if (!isLoading) {
                            if (!recyclerView.canScrollVertically(1)) {
                                page++
                                addItemToPage(item)
                            }
                        }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = adapter
        val itemForEachPage = ArrayList<Data>()
        val start = (page * limit) + 1
        val end = (page * limit) + limit
        for (i in start..end) {
            itemForEachPage.add(item[i - 1])
        }
        adapter.addItem(itemForEachPage)
        adapter.onImageClick = { data ->
            items.add(CartData(data.title, data.price))
        }
    }

    private fun addItemToPage(item: ArrayList<Data>) {
        isLoading = true
        progressBar?.visibility = View.VISIBLE
        val itemForEachPage = ArrayList<Data>()
        val start = (page * limit) + 1
        val end = (page * limit) + limit
        for (i in start..end) {
            itemForEachPage.add(item[i - 1])
        }
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.addItem(itemForEachPage)
            isLoading = false
            progressBar?.visibility = View.GONE
        }, 1000)
    }
}