package dev.mcd.pilotlog.data.logbook

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.mcd.pilotlog.data.common.proto.serialized
import dev.mcd.pilotlog.data.common.proto.toDomain
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import dev.mcd.pilotlog.domain.logbook.LogbookEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.logEntryDataStore by dataStore("log_entry.pb", LogSerializer)

class LogbookEntryRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : LogbookEntryRepository {

    private val dataStore = context.logEntryDataStore

    override fun get(): Flow<List<LogbookEntry>> {
        return dataStore.data.map { log ->
            log.entriesList.map { it.toDomain }
        }
    }

    override suspend fun save(logbookEntry: LogbookEntry) {
        dataStore.updateData { log ->
            log.toBuilder()
                .addEntries(logbookEntry.serialized)
                .build()
        }
    }
}
