package dev.mcd.pilotlog.data.destination

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.mcd.pilotlog.domain.destination.Destination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DestinationDataStore {
    suspend fun destinations(): Flow<List<Destination>>
    suspend fun save(destination: Destination)
    suspend fun remove(destination: Destination)
}

private val Context.destinationDataStore by dataStore("destination.pb", DestinationSerializer)

class DestinationDataStoreImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : DestinationDataStore {

    val dataStore = context.destinationDataStore

    override suspend fun destinations(): Flow<List<Destination>> {
        return dataStore.data.map {
            it.destinationsList.map { destination ->
                destination.toDomain
            }
        }
    }

    override suspend fun save(destination: Destination) {
        val existing = dataStore.data.first().destinationsList.indexOfFirst {
            it.toDomain == destination
        }

        if (existing != -1) {
            return
        }

        dataStore.updateData {
            it.toBuilder()
                .addDestinations(destination.serialized)
                .build()
        }
    }

    override suspend fun remove(destination: Destination) {
        dataStore.updateData {
            it.toBuilder()
                .removeDestinations(
                    it.destinationsList.indexOfFirst { d ->
                        d.toDomain == destination
                    }
                )
                .build()
        }
    }
}
