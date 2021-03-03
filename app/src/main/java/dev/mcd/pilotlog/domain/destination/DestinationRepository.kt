package dev.mcd.pilotlog.domain.destination

import kotlinx.coroutines.flow.Flow

interface DestinationRepository {
    suspend fun get(): Flow<List<Destination>>
    suspend fun save(destination: Destination)
    suspend fun remove(destination: Destination)
}
