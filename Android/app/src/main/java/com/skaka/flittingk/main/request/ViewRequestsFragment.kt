package com.skaka.flittingk.main.request

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skaka.flittingk.R
import com.skaka.flittingk.main.MainActivity
import com.skaka.flittingk.model.RequestDTO
import com.skaka.flittingk.util.*
import com.skaka.flittingk.util.retrofit.API
import kotlinx.android.synthetic.main.fragment_view_requests.*
import javax.inject.Inject

class ViewRequestsFragment : Fragment() {
    val requests = ArrayList<RequestDTO>()
    val adapter = RequestsAdapter(requests)

    @Inject lateinit var db: AppDatabase
    @Inject lateinit var api: API

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DI.component.inject(this)
        (activity as MainActivity).setTitle("Incoming Requests")
        initRecycler()
        initSearchView()
    }

    private fun initSearchView() {
        requestsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = true
            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter(p0 ?: "")
                return true
            }
        })
    }

    fun initRecycler() {
        requestsRecycler.layoutManager = LinearLayoutManager(context)
        requestsRecycler.adapter = adapter

        launchAsync {
            val res = asyncAwait { api.getRequestsTo(4).execute() }
            requests.clearAndAddAll(res.body() ?: listOf())
            adapter.filter("")
        }
    }
}