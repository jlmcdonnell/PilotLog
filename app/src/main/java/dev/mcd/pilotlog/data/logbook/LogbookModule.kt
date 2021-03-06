package dev.mcd.pilotlog.data.logbook

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.pilotlog.data.entry.SaveLogbookEntryImpl
import dev.mcd.pilotlog.domain.logbook.SaveLogbookEntry

@Module
@InstallIn(SingletonComponent::class)
abstract class LogbookModule {

    @Binds
    abstract fun saveLogbookEntry(saveLogbookEntryImpl: SaveLogbookEntryImpl): SaveLogbookEntry
}
