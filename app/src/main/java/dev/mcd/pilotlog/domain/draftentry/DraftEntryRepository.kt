package dev.mcd.pilotlog.domain.draftentry

import dev.mcd.pilotlog.domain.destination.Destination
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import kotlinx.coroutines.flow.Flow

interface DraftEntryRepository {
    suspend fun getEntries(): Flow<LogbookEntry>
    suspend fun updateEntry(logbookEntry: LogbookEntry)
    suspend fun getEntry(): LogbookEntry
    suspend fun updateToDestination(toDestination: Destination)
    suspend fun updateFromDestination(fromDestination: Destination)
    suspend fun deleteEntry()
}
