package com.skaka.flittingk.main.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skaka.flittingk.R
import com.skaka.flittingk.main.MainActivity
import com.skaka.flittingk.model.ReviewDTO
import com.skaka.flittingk.model.UserDetail
import com.skaka.flittingk.model.UserProfile
import com.skaka.flittingk.util.*
import com.skaka.flittingk.util.retrofit.API
import kotlinx.android.synthetic.main.fragment_view_profile.*
import kotlinx.android.synthetic.main.row_request.view.*
import javax.inject.Inject

class ViewProfileFragment : Fragment() {
    @Inject lateinit var api: API

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_view_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DI.component.inject(this)

        launchAsync {
            val profile = asyncAwait {
                api.getUser(4).execute().body() ?: UserProfile("", "", UserDetail("", "", ""))
            }
            if (profile.details.email.isEmpty()) {
                Toast.makeText(activity, "Error fetching Profile information.", Toast.LENGTH_SHORT).show()
                NavHostFragment.findNavController(this@ViewProfileFragment).popBackStack()
            } else {
                (activity as MainActivity).setTitle(profile.getName())

                Glide.with(activity as MainActivity)
                    .load(BaseURLImage+profile.image)
                    .apply(RequestOptions.centerCropTransform().placeholder(R.drawable.bird_white))
                    .into(helperImage)
//
                listingDateText.visibility = View.GONE
                helperPhoneText.text = profile.phone
                helperEmailText.text = profile.details.email
            }
        }

        launchAsync {
            val reviews = asyncAwait { api.getReviewsOf(4).execute().body() ?: listOf<ReviewDTO>() }

            Log.d("ViewProfileFragment", "onViewCreated: " + reviews.size)
            reviewTitle.text = reviews[0].newbie.getName()
            reviewContent.text = reviews[0].text
            reviewRating.text = "%d/5".format(reviews[0].rating)
            Glide.with(activity as MainActivity)
                .load(BaseURLImage+reviews[0].newbie.image)
                .apply(RequestOptions.centerCropTransform().placeholder(R.drawable.bird_white))
                .into(reviewImage1)

            reviewTitle2.text = reviews[1].newbie.getName()
            reviewContent2.text = reviews[1].text
            reviewRating2.text = "%d/5".format(reviews[1].rating)
            Glide.with(activity as MainActivity)
                .load(BaseURLImage+reviews[1].newbie.image)
                .apply(RequestOptions.centerCropTransform().placeholder(R.drawable.bird_white))
                .into(reviewImage2)
        }
    }
}