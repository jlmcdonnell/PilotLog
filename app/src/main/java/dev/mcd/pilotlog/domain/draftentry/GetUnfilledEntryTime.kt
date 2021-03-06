package dev.mcd.pilotlog.domain.draftentry

interface GetUnfilledEntryTime {
    suspend fun execute(): Long
}
