package dev.mcd.pilotlog.domain.aircraft

import kotlinx.coroutines.flow.Flow

interface AircraftRepository {
    suspend fun get(): Flow<List<Aircraft>>
    suspend fun save(aircraft: Aircraft)
    suspend fun remove(aircraft: Aircraft)
}
