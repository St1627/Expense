package com.ztt.expense.data.datasource

import com.ztt.expense.data.local.database.entity.Home
import kotlinx.coroutines.flow.Flow

interface IHomeDataSource {

    suspend fun getAll(): Flow<List<Home>>

    suspend fun getHome(homeId: Int): Flow<Home>

    suspend fun addHome(home:Home):Long
}