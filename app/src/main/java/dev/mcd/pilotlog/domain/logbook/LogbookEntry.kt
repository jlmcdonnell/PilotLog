package dev.mcd.pilotlog.domain.logbook

import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.domain.aircraft.isValid
import dev.mcd.pilotlog.domain.location.Location
import dev.mcd.pilotlog.domain.location.isValid
import dev.mcd.pilotlog.domain.time.DateString
import dev.mcd.pilotlog.domain.time.TimeString
import dev.mcd.pilotlog.domain.time.isValidDate

data class LogbookEntry(
    val aircraft: Aircraft,
    val arrivalTime: TimeString,
    val captain: String,
    val date: DateString,
    val departureTime: TimeString,
    val departure: Location,
    val holdersOperatingCapacity: String,
    val landingCount: Int,
    val remarks: String,
    val secondsDay: Int,
    val secondsNight: Int,
    val secondsInstrument: Int,
    val secondsInstrumentSim: Int,
    val takeOffCount: Int,
    val arrival: Location,
)

val LogbookEntry.validate: LogbookEntryError?
    get() {
        return when {
            aircraft.isValid.not() -> LogbookEntryError.Aircraft
            arrivalTime.isBlank() -> LogbookEntryError.ArrivalTime
            captain.isBlank() -> LogbookEntryError.Captain
            date.isValidDate -> LogbookEntryError.Date
            departureTime.isBlank() -> LogbookEntryError.DepartureTime
            departure.isValid.not() -> LogbookEntryError.Departure
            holdersOperatingCapacity.isBlank() -> LogbookEntryError.HoldersOperatingCapacity
            landingCount < 0 -> LogbookEntryError.LandingCount
            secondsDay < 0 -> LogbookEntryError.SecondsDay
            secondsNight < 0 -> LogbookEntryError.SecondsNight
            secondsInstrument < 0 -> LogbookEntryError.SecondsInstrument
            secondsInstrumentSim < 0 -> LogbookEntryError.SecondsInstrumentSim
            takeOffCount < 0 -> LogbookEntryError.TakeOffCount
            arrival.isValid.not() -> LogbookEntryError.Arrival
            else -> null
        }
    }
