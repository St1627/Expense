package com.ztt.expense.data.local.database.access

import android.content.Context
import androidx.room.Room

class ExpenseDBHelper private constructor() {

    companion object {
        private var INSTANCE: ExpenseDatabase? = null
        private val mLock = Any()

        fun getInstance(context: Context): ExpenseDatabase = INSTANCE ?: synchronized(mLock) {
            Room.databaseBuilder(context.applicationContext, ExpenseDatabase::class.java, "expense")
                .build().apply { INSTANCE = this }
        }
    }
}