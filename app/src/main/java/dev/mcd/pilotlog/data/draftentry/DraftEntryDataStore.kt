package dev.mcd.pilotlog.data.draftentry

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.mcd.pilotlog.data.common.proto.serialized
import dev.mcd.pilotlog.data.common.proto.toDomain
import dev.mcd.pilotlog.data.entry.LogbookEntrySerializer
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DraftEntryDataStore {
    suspend fun save(entry: LogbookEntry)
    suspend fun newEntries(): Flow<LogbookEntry>
    suspend fun delete()
}

private val Context.draftEntryDataStore by dataStore("draft_entry.pb", LogbookEntrySerializer)

class DraftEntryDataStoreImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : DraftEntryDataStore {

    private val dataStore = context.draftEntryDataStore

    override suspend fun save(entry: LogbookEntry) {
        dataStore.updateData {
            entry.serialized
        }
    }

    override suspend fun newEntries(): Flow<LogbookEntry> {
        return dataStore.data.map { it.toDomain }
    }

    override suspend fun delete() {
        dataStore.updateData { it.defaultInstanceForType }
    }
}
