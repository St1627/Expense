package com.ztt.expense.data.repository

import android.os.Looper
import android.util.Log
import com.ztt.expense.data.datasource.IHomeDataSource
import com.ztt.expense.data.local.database.entity.Home
import com.ztt.expense.domain.repository.IHomeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HomeRepository(private val homeSrc: IHomeDataSource) : IHomeRepo {
    override suspend fun getAllHomes(): Flow<List<Home>> {
        return homeSrc.getAll()
    }

    override suspend fun addHome(home: Home): Long {
        return withContext(Dispatchers.IO) {
            Log.e("scope", "is main2 ? "+(Looper.getMainLooper() == Looper.myLooper()))
            homeSrc.addHome(home)
        }
    }
}