package dev.mcd.pilotlog.data.aircraft

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.domain.aircraft.AircraftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AircraftRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : AircraftRepository {

    companion object {
        private val Context.aircraftDataStore by dataStore("aircraft.pb", AircraftSerializer)
    }

    private val dataStore = context.aircraftDataStore

    override suspend fun get(): Flow<List<Aircraft>> {
        return dataStore.data.map {
            it.aircraftsList.map { destination ->
                destination.toDomain
            }
        }
    }

    override suspend fun save(aircraft: Aircraft) {
        val existing = dataStore.data.first().aircraftsList.indexOfFirst {
            it.toDomain == aircraft
        }

        if (existing != -1) {
            return
        }

        dataStore.updateData {
            it.toBuilder()
                .addAircrafts(aircraft.serialized)
                .build()
        }
    }

    override suspend fun remove(aircraft: Aircraft) {
        dataStore.updateData {
            it.toBuilder()
                .removeAircrafts(
                    it.aircraftsList.indexOfFirst { d ->
                        d.toDomain == aircraft
                    }
                )
                .build()
        }
    }
}
