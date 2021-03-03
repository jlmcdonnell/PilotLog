package dev.mcd.pilotlog.data.newentry

import dev.mcd.pilotlog.domain.destination.Destination
import dev.mcd.pilotlog.domain.entry.Entry
import dev.mcd.pilotlog.domain.newentry.NewEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class NewEntryRepositoryImpl @Inject constructor(
    private val dataStore: NewEntryDataStore,
) : NewEntryRepository {

    override suspend fun getEntries(): Flow<Entry> {
        return dataStore.newEntries()
    }

    override suspend fun updateEntry(entry: Entry) {
        dataStore.save(entry)
    }

    override suspend fun getEntry(): Entry {
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
