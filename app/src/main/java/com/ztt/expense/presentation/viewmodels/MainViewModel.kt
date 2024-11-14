package com.ztt.expense.presentation.viewmodels

import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ztt.expense.common.DataStoreHelper
import com.ztt.expense.data.local.database.entity.ExpenseItem
import com.ztt.expense.data.local.database.entity.Home
import com.ztt.expense.data.local.database.entity.User
import com.ztt.expense.domain.repository.IExpenseRepo
import com.ztt.expense.domain.repository.IHomeRepo
import com.ztt.expense.domain.repository.IUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val expenseRepo: IExpenseRepo,
    private val homeRepo: IHomeRepo,
    private val userRepo: IUserRepo
) : ViewModel() {

    private var _userId: Long = -1
    private var _homeId: Long = -1

    fun getUserId(): Long {
        return _userId
    }

    fun getHomeId(): Long {
        return _homeId
    }

    private val _listState: MutableStateFlow<List<ExpenseItem>> = MutableStateFlow(emptyList())
    val listState: StateFlow<List<ExpenseItem>> = _listState.asStateFlow()

    fun init() {
        viewModelScope.launch {
            DataStoreHelper.getDataStoreValue("currentUserId")
                .collect { userId ->
                    if (userId.isNullOrEmpty()) {
                        createDefaultUser()
                    } else {
                        _userId = userId.toLong()
                        getHomeId(_userId)
                    }
                }
        }
    }

    private fun createDefaultUser() {
        viewModelScope.launch {
            val defaultHome = Home(0, "default")
            val homeId = homeRepo.addHome(defaultHome)
            if (homeId > 0) {
                val defaultUser = User(0, "default", homeId)
                val userId = userRepo.addUser(defaultUser)
                if (userId > 0) {
                    _userId = userId
                    _homeId = homeId
                    DataStoreHelper.saveDataStoreValue("currentUserId", userId)
                    DataStoreHelper.saveDataStoreValue("currentHomeId", homeId)
                    getExpenseList()
                }
            }
        }
    }

    private fun getHomeId(userId: Long) {
        viewModelScope.launch {
            val user = userRepo.getUserById(userId)
            val homeId = user.homeId
            if (homeId > 0) {
                _homeId = homeId
                getExpenseList()
            }
        }
    }

    private fun getExpenseList() {
        viewModelScope.launch {
            expenseRepo.getExpenseList(_userId, _homeId).collect { list ->
                _listState.value = list
            }
        }
    }

//    private val _delExpenseState: MutableStateFlow<Long> = MutableStateFlow(-1)

    fun deleteExpense(item: ExpenseItem) {
        viewModelScope.launch {
            expenseRepo.deleteExpense(item)
        }
    }
}