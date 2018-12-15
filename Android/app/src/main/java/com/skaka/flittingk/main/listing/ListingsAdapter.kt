package com.skaka.flittingk.main.listing

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skaka.flittingk.R
import com.skaka.flittingk.model.ListingDTO
import com.skaka.flittingk.util.clearAndAddAll
import kotlinx.android.synthetic.main.row_listing.view.*
import java.util.stream.Collectors

class ListingsAdapter(val listings: List<ListingDTO>, val listener: (ListingDTO) -> Unit) :
    RecyclerView.Adapter<ListingViewHolder>() {
    private val results = ArrayList<ListingDTO>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListingViewHolder {
        return ListingViewHolder(
            LayoutInflater.from(p0.context)
                .inflate(R.layout.row_listing, p0, false)
        )
    }

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(lvh: ListingViewHolder, pos: Int) {
        lvh.bind(results[pos])
        lvh.itemView.setOnClickListener { listener(results[pos]) }
    }


    fun filter(q: String) {
        Log.d("RequestsAdapter", "filter: ")
        if (!q.isBlank()) {
            results.clear()
            results.addAll(listings.stream()
                .filter {
                    it.userProfile.getName().contains(q, true)
                            || it.description.contains(q, true)
                            || it.city.contains(q, true)
                            || it.country.contains(q, true)
                }
                .collect(Collectors.toList()))
        } else {
            Log.d("RequestsAdapter", "filter: else")
            results.clearAndAddAll(listings)
        }

        notifyDataSetChanged()
    }
}

class ListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(listing: ListingDTO) = with(itemView) {
        listingTitle.text = listing.userProfile.getName()
        listingLocation.text = context.getString(R.string.location_string)
            .format(listing.city, listing.country)
    }
}