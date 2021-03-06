package dev.mcd.pilotlog.domain.logbook

interface SaveLogbookEntry {
    sealed class Result {
        object Success : Result()
        class Error(val error: LogbookEntryError) : Result()
    }

    suspend fun execute(): Result
}
