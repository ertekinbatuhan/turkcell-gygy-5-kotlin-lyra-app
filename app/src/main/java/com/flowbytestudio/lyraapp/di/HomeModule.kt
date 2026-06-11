package com.flowbytestudio.lyraapp.di

import com.flowbytestudio.lyraapp.data.home.HomeRepository
import com.flowbytestudio.lyraapp.data.home.FakeHomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * [HomeRepository] arayüzünü somut implementasyonuna ([FakeHomeRepository]) bağlar.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(impl: FakeHomeRepository): HomeRepository
}
