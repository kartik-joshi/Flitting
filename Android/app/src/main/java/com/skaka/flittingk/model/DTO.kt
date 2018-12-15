package com.skaka.flittingk.model

public data class UserDetail(
    var first_name: String,
    var last_name: String,
    var email: String
)

public class UserProfile(
    var image: String,
    var phone: String,
    var details: UserDetail
) {
    fun getName(): String = details.first_name + " " + details.last_name
}

public data class RequestDTO(
    var id: Int,
    var description: String,
    var isAccepted: Boolean,
    var startDate: String,
    var endDate: String,
    var date_posted: String,
    var userProfile: UserProfile,
    var helper: Int,
    var newbie: Int,
    var listing: Int
)

public data class ListingDTO(
    var id: Int,
    var userProfile: UserProfile,
    var city: String,
    var country: String,
    var description: String,
    var category: String,
    var date_posted: String,
    var user: Int
)

public data class ReviewDTO(
    var helper: Int,
    var newbie: UserProfile,
    var text: String,
    var rating: Int,
    var date_posted: String
)