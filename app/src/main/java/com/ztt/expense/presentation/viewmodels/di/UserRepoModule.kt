package com.ztt.expense.presentation.viewmodels.di

import com.ztt.expense.ExpenseApplication
import com.ztt.expense.data.datasource.IHomeDataSource
import com.ztt.expense.data.datasource.IUserDataSource
import com.ztt.expense.data.datasource.impl.HomeDataSourceImpl
import com.ztt.expense.data.datasource.impl.UserDataSourceImpl
import com.ztt.expense.data.local.database.access.ExpenseDBHelper
import com.ztt.expense.data.local.database.dao.HomeDao
import com.ztt.expense.data.local.database.dao.UserDao
import com.ztt.expense.data.repository.HomeRepository
import com.ztt.expense.data.repository.UserRepository
import com.ztt.expense.domain.repository.IHomeRepo
import com.ztt.expense.domain.repository.IUserRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object UserRepoModule {


    @Provides
    fun provideRepo(src: IUserDataSource): IUserRepo {
        return UserRepository(src)
    }

    @Provides
    fun provideDataSource(dao: UserDao): IUserDataSource {
        return UserDataSourceImpl(dao)
    }

    @Provides
    fun provideDao(): UserDao {
        return ExpenseDBHelper.getInstance(ExpenseApplication.appContext).userDao()
    }
}