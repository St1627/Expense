package com.ztt.expense.data.repository

import android.os.Looper
import android.util.Log
import com.ztt.expense.data.datasource.IExpenseDataSource
import com.ztt.expense.data.local.database.entity.ExpenseItem
import com.ztt.expense.domain.repository.IExpenseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val expenseSrc: IExpenseDataSource) :
    IExpenseRepo {
    override suspend fun getExpenseList(userId: Long, homeId: Long): Flow<List<ExpenseItem>> {
        return expenseSrc.getExpenseList(userId, homeId)
    }

    override suspend fun addNewExpense(item: ExpenseItem): Long {
        return withContext(Dispatchers.IO) {
            expenseSrc.addExpense(item)
        }
    }

    override suspend fun deleteExpense(item: ExpenseItem): Int {
        return withContext(Dispatchers.IO) {
            expenseSrc.deleteExpense(item)
        }
    }
}