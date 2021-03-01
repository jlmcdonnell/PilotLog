package dev.mcd.pilotlog.domain.entry.interactor

import dev.mcd.pilotlog.domain.date.DateString
import dev.mcd.pilotlog.domain.date.TimeProvider
import javax.inject.Inject

class GetNewEntryDetails @Inject constructor(
    private val timeProvider: TimeProvider,
) {

    data class Result(val date: DateString)

    suspend fun execute(): Result {
        return Result(timeProvider.dateString)
    }

}
