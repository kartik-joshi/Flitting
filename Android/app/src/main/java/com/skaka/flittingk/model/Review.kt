package com.skaka.flittingk.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Review(
    @PrimaryKey val id: String,
    val helperId: String,
    val newbieId: String,
    var text: String,
    var rating: Int
)

@Dao
interface ReviewDao