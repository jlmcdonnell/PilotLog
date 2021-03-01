package dev.mcd.pilotlog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.pilotlog.data.date.TimeProviderImpl
import dev.mcd.pilotlog.domain.date.TimeProvider

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun timeProvider(): TimeProvider = TimeProviderImpl()
}
