package dev.mcd.pilotlog.data.destination

import dev.mcd.pilotlog.domain.destination.Destination
import dev.mcd.pilotlog.domain.destination.DestinationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DestinationRepositoryImpl @Inject constructor(
    private val dataStore: DestinationDataStore,
) : DestinationRepository {
    override suspend fun get(): Flow<List<Destination>> {
        return dataStore.destinations()
    }

    override suspend fun save(destination: Destination) {
        dataStore.save(destination)
    }

    override suspend fun remove(destination: Destination) {
        dataStore.remove(destination)
    }
}
