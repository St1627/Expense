package com.ztt.expense.data.datasource.impl

import com.ztt.expense.data.datasource.IUserDataSource
import com.ztt.expense.data.local.database.dao.UserDao
import com.ztt.expense.data.local.database.entity.User
import kotlinx.coroutines.flow.Flow

class UserDataSourceImpl(private val dao: UserDao) : IUserDataSource {
    override suspend fun getAll(): Flow<List<User>> {
        return dao.getAll()
    }

    override suspend fun getUser(userId: Long): User {
        return dao.getUser(userId)
    }

    override suspend fun addUser(user: User): Long {
        return dao.addUser(user)
    }

}