package com.skaka.flittingk.main.listing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.skaka.flittingk.R
import com.skaka.flittingk.main.MainActivity
import com.skaka.flittingk.model.ListingDTO
import kotlinx.android.synthetic.main.fragment_single_listing.*

class ViewSingleListingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_single_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ViewSingleListingFragme", "onViewCreated: $arguments")
        val listingDTO = Gson().fromJson(arguments?.getString("listing"), ListingDTO::class.java)

        listingDateText.text = getString(R.string.location_string).format(listingDTO.city, listingDTO.country)
        helperEmailText.text = listingDTO.userProfile.details.email
        helperPhoneText.text = listingDTO.userProfile.phone
        descriptionText.text = listingDTO.description

        (activity as MainActivity).setTitle(listingDTO.userProfile.getName())
    }
}