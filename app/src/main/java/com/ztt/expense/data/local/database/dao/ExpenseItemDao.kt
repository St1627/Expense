package com.ztt.expense.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ztt.expense.data.local.database.entity.ExpenseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseItemDao {

    @Query("SELECT * FROM ExpenseItem")
    fun getAll(): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM EXPENSEITEM WHERE user_id == :userId and home_id == :homeId order by purchase_time desc")
    fun getByUserAndHome(userId: Long, homeId: Long): Flow<List<ExpenseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewItem(item: ExpenseItem): Long

    @Update
    fun updateItem(item: ExpenseItem): Int

    @Delete
    fun deleteItem(item: ExpenseItem): Int
}