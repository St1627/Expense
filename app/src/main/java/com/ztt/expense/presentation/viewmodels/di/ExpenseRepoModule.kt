package com.ztt.expense.presentation.viewmodels.di

import com.ztt.expense.ExpenseApplication
import com.ztt.expense.data.datasource.IExpenseDataSource
import com.ztt.expense.data.datasource.impl.ExpenseDataSourceImpl
import com.ztt.expense.data.local.database.access.ExpenseDBHelper
import com.ztt.expense.data.local.database.dao.ExpenseItemDao
import com.ztt.expense.data.repository.ExpenseRepository
import com.ztt.expense.domain.repository.IExpenseRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object ExpenseRepoModule {

    @Provides
    fun provideRepo(expenseSrc: IExpenseDataSource): IExpenseRepo {
        return ExpenseRepository(expenseSrc)
    }

    @Provides
    fun provideDataSource(dao: ExpenseItemDao): IExpenseDataSource {
        return ExpenseDataSourceImpl(dao)
    }

    @Provides
    fun provideDao(): ExpenseItemDao {
        return ExpenseDBHelper.getInstance(ExpenseApplication.appContext).expenseDao()
    }
}