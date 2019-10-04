package com.prography.prography_androidstudy.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE email = (:email)")
    fun findUser(email: String): List<User>

    @Query("SELECT * FROM user WHERE email = (:email)")
    fun userLogin(email: String, pw: String): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
}