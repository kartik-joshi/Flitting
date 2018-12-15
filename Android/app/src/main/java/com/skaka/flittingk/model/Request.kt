package com.skaka.flittingk.model

import android.arch.persistence.room.*

@Entity
data class Request(
    @PrimaryKey val id: Int,
    var userId: Int,
    val listingId: Int,
    val description: String,
    var isAccepted: Boolean,
    val startDate: String,
    val endDate: String
)

@Dao
interface RequestDao {
    @Query("SELECT * FROM request WHERE listingId IN (:listings)")
    fun getRequestsByListings(listings: List<String>): List<Request>

    @Insert
    fun insertAll(vararg request: Request)

    @Update
    fun update(vararg request: Request)

    @Query("SELECT * FROM request WHERE id = :id LIMIT 1")
    fun getSingle(id: Int): Request

    @Query("SELECT * FROM request")
    fun getAll(): List<Request>
}

//data class RequestDto(
//    val id: Int,
//    var userId: Int,
//    val listingId: Int,
//    val description: String,
//    var isAccepted: Boolean,
//    val startDate: String,
//    val endDate: String,
//    val userName: String
//)