package com.ztt.expense.data.datasource

import com.ztt.expense.data.local.database.entity.User
import kotlinx.coroutines.flow.Flow

interface IUserDataSource {
    suspend fun getAll(): Flow<List<User>>

    suspend fun getUser(userId: Long): User

    suspend fun addUser(user: User): Long
}