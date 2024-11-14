package com.ztt.expense.data.datasource.impl

import com.ztt.expense.data.datasource.IHomeDataSource
import com.ztt.expense.data.local.database.dao.HomeDao
import com.ztt.expense.data.local.database.entity.Home
import kotlinx.coroutines.flow.Flow

class HomeDataSourceImpl(private val dao: HomeDao) : IHomeDataSource {
    override suspend fun getAll(): Flow<List<Home>> {
        return dao.getAll()
    }

    override suspend fun getHome(homeId: Int): Flow<Home> {
        return dao.getHome(homeId)
    }

    override suspend fun addHome(home: Home):Long {
        return dao.addHome(home)
    }
}