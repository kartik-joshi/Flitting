package com.skaka.flittingk.main.listing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import com.skaka.flittingk.R
import com.skaka.flittingk.main.MainActivity
import com.skaka.flittingk.model.ListingDTO
import com.skaka.flittingk.util.*
import com.skaka.flittingk.util.retrofit.API
import kotlinx.android.synthetic.main.fragment_view_listings.*
import javax.inject.Inject

class ViewListingsFragment : Fragment() {
    @Inject lateinit var db: AppDatabase
    @Inject lateinit var api: API
    private var listings = ArrayList<ListingDTO>()

    private val adapter: ListingsAdapter = ListingsAdapter(listings) {
        val bundle = Bundle()
        bundle.putString("listing", Gson().toJson(it))
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_viewListingsFragment_to_viewSingleListingFragment, bundle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_listings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DI.component.inject(this)
        initRecycler()
        if (arguments == null || arguments?.size() == 0) {
            (activity as MainActivity).setTitle("My Listings")

            launchAsync {
                val res = asyncAwait { api.getListingsOf(4).execute() }
                listings.clearAndAddAll(res.body() ?: listOf())
                adapter.filter("")
            }

            listingsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean = true
                override fun onQueryTextChange(p0: String?): Boolean {
                    adapter.filter(p0 ?: "")
                    return true
                }
            })
        } else {
            (activity as MainActivity).setTitle("Search Listings")
            listingsSearchView.setQuery(arguments?.getString("query"), false)
            launchAsync {
                val res = asyncAwait { api.getSearchResults(listingsSearchView.query.toString()).execute() }
                listings.clearAndAddAll(res.body() ?: listOf())
                adapter.filter(listingsSearchView.query.toString())
            }

            listingsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean = true
                override fun onQueryTextChange(p0: String?): Boolean {
                    launchAsync {
                        val res = asyncAwait { api.getSearchResults(listingsSearchView.query.toString()).execute() }
                        listings.clearAndAddAll(res.body() ?: listOf())
                        adapter.filter(listingsSearchView.query.toString())
                    }
                    return true
                }
            })
        }
    }

    private fun initRecycler() {
        listingsRecycler.layoutManager = LinearLayoutManager(context)
        listingsRecycler.adapter = adapter
    }

    //todo filter recycler based on search string
}