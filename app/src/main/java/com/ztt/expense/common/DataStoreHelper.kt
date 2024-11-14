package com.ztt.expense.common

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ztt.expense.ExpenseApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreHelper {
    companion object {
        fun getDataStoreValue(key: String): Flow<String?> {
            val k = stringPreferencesKey(key)
            val flow = ExpenseApplication.dataStore.data.map {
                it[k]
            }
            return flow
        }

        suspend fun saveDataStoreValue(key: String, value: Any) {
            val k = stringPreferencesKey(key)
            val flow = ExpenseApplication.dataStore.edit { map ->
                map[k] = value.toString()
            }
        }
    }
}