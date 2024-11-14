package com.ztt.expense.domain.repository

import com.ztt.expense.data.local.database.entity.ExpenseItem
import kotlinx.coroutines.flow.Flow

interface IExpenseRepo {

    suspend fun getExpenseList(userId: Long, homeId: Long): Flow<List<ExpenseItem>>

    suspend fun addNewExpense(item: ExpenseItem): Long

    suspend fun deleteExpense(item: ExpenseItem): Int
}