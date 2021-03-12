package dev.mcd.pilotlog.data.location

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.mcd.pilotlog.domain.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface LocationDataStore {
    suspend fun locations(): Flow<List<Location>>
    suspend fun save(location: Location)
    suspend fun remove(location: Location)
}

private val Context.locationDataStore by dataStore("location.pb", LocationSerializer)

class LocationDataStoreImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : LocationDataStore {

    val dataStore = context.locationDataStore

    override suspend fun locations(): Flow<List<Location>> {
        return dataStore.data.map {
            it.locationList.map { location ->
                location.toDomain
            }
        }
    }

    override suspend fun save(location: Location) {
        val existing = dataStore.data.first().locationList.indexOfFirst {
            it.toDomain==location
        }

        if (existing!=-1) {
            return
        }

        dataStore.updateData {
            it.toBuilder()
                .addLocation(location.serialized)
                .build()
        }
    }

    override suspend fun remove(location: Location) {
        dataStore.updateData {
            it.toBuilder()
                .removeLocation(
                    it.locationList.indexOfFirst { d ->
                        d.toDomain==location
                    }
                )
                .build()
        }
    }
}
