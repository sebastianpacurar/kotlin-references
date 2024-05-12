package com.example.androidReferences.loginApp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidReferences.loginApp.data.models.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE user_name=:userName AND user_pass=:userPass ")
    fun getUser(userName: String, userPass: String): Flow<User>
}
