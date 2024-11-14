package com.ztt.expense.data.datasource

import com.ztt.expense.data.local.database.entity.ExpenseItem
import kotlinx.coroutines.flow.Flow

interface IExpenseDataSource {
    suspend fun addExpense(expenseItem: ExpenseItem): Long

    suspend fun updateExpense(expenseItem: ExpenseItem): Int

    suspend fun deleteExpense(expenseItem: ExpenseItem): Int

    suspend fun getExpenseList(userId: Long, homeId: Long): Flow<List<ExpenseItem>>
}