package com.ztt.expense.domain.repository

import com.ztt.expense.data.local.database.entity.User
import kotlinx.coroutines.flow.Flow

interface IUserRepo {

    suspend fun getAllUsers():Flow<List<User>>

    suspend fun getUserById(userId:Long):User

    suspend fun addUser(user:User):Long
}