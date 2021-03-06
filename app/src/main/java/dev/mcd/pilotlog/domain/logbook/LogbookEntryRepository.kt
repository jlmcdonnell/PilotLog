package dev.mcd.pilotlog.domain.logbook

import kotlinx.coroutines.flow.Flow

interface LogbookEntryRepository {
    fun get(): Flow<List<LogbookEntry>>
    suspend fun save(logbookEntry: LogbookEntry)
}
