package dev.mcd.pilotlog.domain.draftentry

import dev.mcd.pilotlog.domain.location.Location
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import kotlinx.coroutines.flow.Flow

interface DraftEntryRepository {
    suspend fun getEntries(): Flow<LogbookEntry>
    suspend fun updateEntry(logbookEntry: LogbookEntry)
    suspend fun getEntry(): LogbookEntry
    suspend fun updateArrival(toLocation: Location)
    suspend fun updateDeparture(fromLocation: Location)
    suspend fun deleteEntry()
}
