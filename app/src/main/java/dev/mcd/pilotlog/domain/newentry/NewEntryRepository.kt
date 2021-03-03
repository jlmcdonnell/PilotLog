package dev.mcd.pilotlog.domain.newentry

import dev.mcd.pilotlog.domain.destination.Destination
import dev.mcd.pilotlog.domain.entry.Entry
import kotlinx.coroutines.flow.Flow

interface NewEntryRepository {
    suspend fun getEntries(): Flow<Entry>
    suspend fun updateEntry(entry: Entry)
    suspend fun getEntry(): Entry
    suspend fun updateToDestination(toDestination: Destination)
    suspend fun updateFromDestination(fromDestination: Destination)
    suspend fun deleteEntry()
}
