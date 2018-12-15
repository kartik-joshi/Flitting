package com.skaka.flittingk.util

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.skaka.flittingk.model.*

@Database(
    entities = [User::class, Listing::class, Review::class, Request::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun listingDao(): ListingDao
    abstract fun requestDao(): RequestDao
    abstract fun reviewDao(): ReviewDao
}