package com.ztt.expense.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ztt.expense.data.local.database.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM User WHERE uid == :userId")
    suspend fun getUser(userId: Long): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User): Long
}