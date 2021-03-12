package dev.mcd.pilotlog.domain.location

import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun get(): Flow<List<Location>>
    suspend fun save(location: Location)
    suspend fun remove(location: Location)
}
