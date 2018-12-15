package com.skaka.flittingk.util.retrofit

import com.skaka.flittingk.model.ListingDTO
import com.skaka.flittingk.model.RequestDTO
import com.skaka.flittingk.model.ReviewDTO
import com.skaka.flittingk.model.UserProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("api/requests/{requestId}")
    fun getRequest(@Path("requestId") requestId: Int): Call<RequestDTO>

    @GET("api/listings/{listingId}")
    fun getListing(@Path("listingId") listingId: Int): Call<ListingDTO>

    @GET("api/listings/search")
    fun getSearchResults(@Query("q") query: String): Call<List<ListingDTO>>

    @GET("api/users/{userId}")
    fun getUser(@Path("userId") userId: Int): Call<UserProfile>

    @GET("api/users/{userId}/listings")
    fun getListingsOf(@Path("userId") userId: Int): Call<List<ListingDTO>>

    @GET("api/users/{userId}/reviews")
    fun getReviewsOf(@Path("userId") userId: Int): Call<List<ReviewDTO>>

    @GET("api/users/{userId}/requests")
    fun getRequestsTo(@Path("userId") userId: Int): Call<List<RequestDTO>>

//    # path('api/requests/create', views.createRequest) #create new request via post
//    # path('api/listings/create', views.createListing)
}