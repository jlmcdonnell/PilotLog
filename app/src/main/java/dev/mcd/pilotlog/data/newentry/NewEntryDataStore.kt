package dev.mcd.pilotlog.data.newentry

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.mcd.pilotlog.data.common.proto.serialized
import dev.mcd.pilotlog.data.common.proto.toDomain
import dev.mcd.pilotlog.data.entry.EntrySerializer
import dev.mcd.pilotlog.domain.entry.Entry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface NewEntryDataStore {
    suspend fun save(newEntry: Entry)
    suspend fun newEntries(): Flow<Entry>
    suspend fun delete()
}

private val Context.newEntryDataStore by dataStore("new_entry.pb", EntrySerializer)

class NewEntryDataStoreImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : NewEntryDataStore {

    private val dataStore = context.newEntryDataStore

    override suspend fun save(newEntry: Entry) {
        dataStore.updateData {
            newEntry.serialized
        }
    }

    override suspend fun newEntries(): Flow<Entry> {
        return dataStore.data.map { it.toDomain }
    }

    override suspend fun delete() {
        dataStore.updateData { it.defaultInstanceForType }
    }
}
