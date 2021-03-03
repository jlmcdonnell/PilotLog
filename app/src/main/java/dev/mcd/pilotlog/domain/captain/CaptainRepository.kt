package dev.mcd.pilotlog.domain.captain

import kotlinx.coroutines.flow.Flow

interface CaptainRepository {
    fun get(): Flow<List<Captain>>
    suspend fun save(captain: Captain)
}
