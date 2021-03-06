package dev.mcd.pilotlog.data.draftentry

import dev.mcd.pilotlog.domain.destination.Destination
import dev.mcd.pilotlog.domain.draftentry.DraftEntryRepository
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DraftEntryRepositoryImpl @Inject constructor(
    private val dataStore: DraftEntryDataStore,
) : DraftEntryRepository {

    override suspend fun getEntries(): Flow<LogbookEntry> {
        return dataStore.newEntries()
    }

    override suspend fun updateEntry(logbookEntry: LogbookEntry) {
        val existing = getEntry()
        if (logbookEntry!=existing) {
            dataStore.save(logbookEntry)
        }
    }

    override suspend fun getEntry(): LogbookEntry {
        return dataStore.newEntries().first()
    }

    override suspend fun updateToDestination(toDestination: Destination) {
        dataStore.save(getEntry().copy(toDestination = toDestination))
    }

    override suspend fun updateFromDestination(fromDestination: Destination) {
        dataStore.save(getEntry().copy(fromDestination = fromDestination))
    }

    override suspend fun deleteEntry() {
        dataStore.delete()
    }
}
