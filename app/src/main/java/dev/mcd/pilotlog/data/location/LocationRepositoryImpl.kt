package dev.mcd.pilotlog.data.location

import dev.mcd.pilotlog.domain.location.Location
import dev.mcd.pilotlog.domain.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val dataStore: LocationDataStore,
) : LocationRepository {
    override suspend fun get(): Flow<List<Location>> {
        return dataStore.locations()
    }

    override suspend fun save(location: Location) {
        dataStore.save(location)
    }

    override suspend fun remove(location: Location) {
        dataStore.remove(location)
    }
}
