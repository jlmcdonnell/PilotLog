package dev.mcd.pilotlog.data.draftentry

import dev.mcd.pilotlog.domain.draftentry.DraftEntryRepository
import dev.mcd.pilotlog.domain.location.Location
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
        if (logbookEntry != existing) {
            dataStore.save(logbookEntry)
        }
    }

    override suspend fun getEntry(): LogbookEntry {
        return dataStore.newEntries().first()
    }

    override suspend fun updateArrival(toLocation: Location) {
        dataStore.save(getEntry().copy(arrival = toLocation))
    }

    override suspend fun updateDeparture(fromLocation: Location) {
        dataStore.save(getEntry().copy(departure = fromLocation))
    }

    override suspend fun deleteEntry() {
        dataStore.delete()
    }
}
