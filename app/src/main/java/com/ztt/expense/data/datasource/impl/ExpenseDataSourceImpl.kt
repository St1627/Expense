package com.ztt.expense.data.datasource.impl

import android.os.Looper
import android.util.Log
import com.ztt.expense.data.datasource.IExpenseDataSource
import com.ztt.expense.data.local.database.dao.ExpenseItemDao
import com.ztt.expense.data.local.database.entity.ExpenseItem
import kotlinx.coroutines.flow.Flow

class ExpenseDataSourceImpl(private val dao: ExpenseItemDao) : IExpenseDataSource {
    override suspend fun addExpense(expenseItem: ExpenseItem):Long {
        return dao.insertNewItem(expenseItem)
    }

    override suspend fun updateExpense(expenseItem: ExpenseItem):Int {
        return dao.updateItem(expenseItem)
    }

    override suspend fun deleteExpense(expenseItem: ExpenseItem):Int {
        return dao.deleteItem(expenseItem)
    }

    override suspend fun getExpenseList(userId: Long, homeId: Long): Flow<List<ExpenseItem>> {
        return dao.getByUserAndHome(userId, homeId)
    }
}