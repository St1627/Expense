package com.ztt.expense.presentation.viewmodels.di

import com.ztt.expense.ExpenseApplication
import com.ztt.expense.data.datasource.IHomeDataSource
import com.ztt.expense.data.datasource.impl.HomeDataSourceImpl
import com.ztt.expense.data.local.database.access.ExpenseDBHelper
import com.ztt.expense.data.local.database.dao.HomeDao
import com.ztt.expense.data.repository.HomeRepository
import com.ztt.expense.domain.repository.IHomeRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object HomeRepoModule {

    @Provides
    fun provideRepo(src: IHomeDataSource): IHomeRepo {
        return HomeRepository(src)
    }

    @Provides
    fun provideDataSource(dao: HomeDao): IHomeDataSource {
        return HomeDataSourceImpl(dao)
    }

    @Provides
    fun provideDao(): HomeDao {
        return ExpenseDBHelper.getInstance(ExpenseApplication.appContext).homeDao()
    }
}