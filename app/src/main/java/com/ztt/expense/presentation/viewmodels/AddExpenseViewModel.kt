package com.ztt.expense.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ztt.expense.data.local.database.entity.ExpenseItem
import com.ztt.expense.domain.repository.IExpenseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expenseRepo: IExpenseRepo
) : ViewModel() {

    private val _addExpenseState: MutableStateFlow<Long> = MutableStateFlow(-1)
    val addExpenseState = _addExpenseState.asStateFlow()

    fun addNewExpense(
        userId: Long,
        homeId: Long,
        name: String,
        price: String,
        time: Long
    ) {
        viewModelScope.launch{
            val expense = ExpenseItem(
                0,
                userId,
                homeId,
                name,
                price,
                "",
                time,
                System.currentTimeMillis()
            )
            _addExpenseState.value = expenseRepo.addNewExpense(expense)
        }
    }
}