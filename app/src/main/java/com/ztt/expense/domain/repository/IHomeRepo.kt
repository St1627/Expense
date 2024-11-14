package com.ztt.expense.domain.repository

import com.ztt.expense.data.local.database.entity.Home
import kotlinx.coroutines.flow.Flow

interface IHomeRepo {
    suspend fun getAllHomes(): Flow<List<Home>>

    suspend fun addHome(home:Home):Long
}