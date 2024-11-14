package com.ztt.expense

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ztt.expense.data.local.database.access.ExpenseDBHelper
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ExpenseApplication : Application() {

    val Context.dataSource: DataStore<Preferences> by preferencesDataStore(name = "common")

    companion object {
        lateinit var appContext: Context
            private set
        lateinit var dataStore: DataStore<Preferences>
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        dataStore = this.dataSource
        ExpenseDBHelper.getInstance(appContext)
    }
}