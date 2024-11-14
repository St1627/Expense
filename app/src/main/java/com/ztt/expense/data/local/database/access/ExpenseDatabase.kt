package com.ztt.expense.data.local.database.access

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ztt.expense.data.local.database.dao.ExpenseItemDao
import com.ztt.expense.data.local.database.dao.HomeDao
import com.ztt.expense.data.local.database.dao.UserDao
import com.ztt.expense.data.local.database.entity.ExpenseItem
import com.ztt.expense.data.local.database.entity.Home
import com.ztt.expense.data.local.database.entity.User

@Database(entities = [ExpenseItem::class, User::class, Home::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseItemDao

    abstract fun homeDao(): HomeDao

    abstract fun userDao(): UserDao
}