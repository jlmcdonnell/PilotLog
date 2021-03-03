package dev.mcd.pilotlog.domain.entry

import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.domain.aircraft.isValid
import dev.mcd.pilotlog.domain.date.DateString
import dev.mcd.pilotlog.domain.destination.Destination
import dev.mcd.pilotlog.domain.destination.isValid

data class Entry(
    val date: DateString,
    val captain: String,
    val holdersOperatingCapacity: String,
    val aircraft: Aircraft,
    val fromDestination: Destination,
    val toDestination: Destination,
)

val Entry.isValid: Boolean
    get() {
        return when {
            date.isBlank() -> false
            captain.isBlank() -> false
            holdersOperatingCapacity.isBlank() -> false
            !fromDestination.isValid -> false
            !toDestination.isValid -> false
            !aircraft.isValid -> false
            else -> true
        }
    }
