package dev.mcd.pilotlog.domain.logbook

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.pilotlog.data.logbook.LogbookEntryRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class LogbookModule {

    @Binds
    abstract fun logEntryRepository(logEntryRepository: LogbookEntryRepositoryImpl): LogbookEntryRepository
}
