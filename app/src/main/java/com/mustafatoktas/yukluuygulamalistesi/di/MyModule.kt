package com.mustafatoktas.yukluuygulamalistesi.di

import android.content.Context
import com.mustafatoktas.yukluuygulamalistesi.data.repositoryImpl.AppRepositoryImpl
import com.mustafatoktas.yukluuygulamalistesi.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MyModule {

    @Provides
    @Singleton
    fun provideAppRepository(@ApplicationContext context: Context): AppRepository {
        return AppRepositoryImpl(context)
    }
}