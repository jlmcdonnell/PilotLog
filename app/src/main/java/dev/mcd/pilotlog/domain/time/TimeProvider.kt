package dev.mcd.pilotlog.domain.time

import java.time.LocalTime

interface TimeProvider {
    val now: Milliseconds
    val today: DateString

    fun parseTime(time: TimeString): LocalTime
    fun formatTime(time: LocalTime): TimeString
}
