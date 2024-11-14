package com.ztt.expense.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ztt.expense.data.local.database.entity.Home
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {

    @Query("SELECT * FROM Home")
    fun getAll(): Flow<List<Home>>

    @Query("SELECT * FROM home WHERE uid == :homeId")
    fun getHome(homeId: Int): Flow<Home>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHome(home: Home): Long
}