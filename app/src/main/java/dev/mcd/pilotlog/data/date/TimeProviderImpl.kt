package dev.mcd.pilotlog.data.date

import dev.mcd.pilotlog.domain.time.DateString
import dev.mcd.pilotlog.domain.time.Milliseconds
import dev.mcd.pilotlog.domain.time.TimeProvider
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import javax.inject.Inject

class TimeProviderImpl @Inject constructor() : TimeProvider {

    override val now: Milliseconds
        get() = System.currentTimeMillis()

    override val today: DateString
        get() = LocalDate.now().toString()

    private val localTimeFormatter: DateTimeFormatter = DateTimeFormatterBuilder()
        .appendValue(ChronoField.HOUR_OF_DAY, 2)
        .appendLiteral(":")
        .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
        .toFormatter()

    override fun parseTime(time: String): LocalTime {
        return LocalTime.from(localTimeFormatter.parse(time))
    }

    override fun formatTime(time: LocalTime): String {
        return localTimeFormatter.format(time)
    }
}
