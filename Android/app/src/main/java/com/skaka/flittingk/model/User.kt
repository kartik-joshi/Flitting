package com.skaka.flittingk.model

import android.arch.persistence.room.*

@Entity
data class User(
    @PrimaryKey val id: Int,
    val email: String,
    var name: String,
    var phone: String,
    var imageUrl: String
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert
    fun insertAll(vararg user: User)

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    fun getUser(id: Int): User
}