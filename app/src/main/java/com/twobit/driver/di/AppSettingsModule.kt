package com.twobit.driver.di

import android.content.Context
import com.twobit.driver.settings.AppSettingsSerializer
import com.twobit.driver.settings.SettingsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppSettingsModule {

    @Provides
    @Singleton
    fun provideAppSettingsSerializer(): AppSettingsSerializer {
        return AppSettingsSerializer
    }

    @Provides
    @Singleton
    fun provideSettingsManager(
        @ApplicationContext context: Context,
        appSettingsSerializer: AppSettingsSerializer
    ): SettingsManager {
        return SettingsManager(context, appSettingsSerializer)
    }
}
