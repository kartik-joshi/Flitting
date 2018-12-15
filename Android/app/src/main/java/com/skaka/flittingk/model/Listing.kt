package com.skaka.flittingk.model

import android.arch.persistence.room.*

@Entity
data class Listing(
    @PrimaryKey val id: Int,
    var userId: Int,
    var city: String,
    var country: String,
    var description: String,
    var categories: String
)

@Dao
interface ListingDao {
    @Query("SELECT * FROM listing WHERE city LIKE :city")
    fun getAllInCity(city: String): List<Listing>

    @Query("SELECT * FROM listing WHERE country LIKE :country")
    fun getAllInCountry(country: String): List<Listing>

    @Insert
    fun insertAll(vararg listing: Listing)

    @Query("SELECT * FROM listing WHERE categories LIKE '%'||:category||'%'")
    fun getAllWithCategory(category: String): List<Listing>

    @Query("SELECT * FROM listing WHERE userId = :id")
    fun getAllOfUser(id: Int): List<Listing>

    @Query("SELECT * FROM listing WHERE id = :id")
    fun getSingle(id: Int) : Listing
}