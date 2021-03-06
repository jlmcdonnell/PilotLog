package dev.mcd.pilotlog.data.date

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.pilotlog.domain.time.TimeProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeModule {

    @Binds
    abstract fun timeProvider(timeProviderImpl: TimeProviderImpl): TimeProvider
}
