package com.ztt.expense.data.repository

import com.ztt.expense.data.datasource.IUserDataSource
import com.ztt.expense.data.local.database.entity.User
import com.ztt.expense.domain.repository.IUserRepo
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userSrc: IUserDataSource) : IUserRepo {
    override suspend fun getAllUsers(): Flow<List<User>> {
        return userSrc.getAll()
    }

    override suspend fun getUserById(userId: Long): User{
        return userSrc.getUser(userId)
    }

    override suspend fun addUser(user: User): Long {
        return userSrc.addUser(user)
    }
}