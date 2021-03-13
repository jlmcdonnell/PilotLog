package dev.mcd.pilotlog.ui.draftentry

import androidx.annotation.StringRes
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.logbook.LogbookEntryError

val LogbookEntryError.errorStringRes: Int
    @StringRes
    get() = when (this) {
        LogbookEntryError.Aircraft -> R.string.entry_error_aircraft
        LogbookEntryError.ArrivalTime -> R.string.entry_error_arrivalTime
        LogbookEntryError.Captain -> R.string.entry_error_captain
        LogbookEntryError.Date -> R.string.entry_error_date
        LogbookEntryError.DepartureTime -> R.string.entry_error_departureTime
        LogbookEntryError.Departure -> R.string.entry_error_departure_location
        LogbookEntryError.HoldersOperatingCapacity -> R.string.entry_error_holders_operating_capacity
        LogbookEntryError.LandingCount -> R.string.entry_error_landing_count
        LogbookEntryError.SecondsDay -> R.string.entry_error_seconds_day
        LogbookEntryError.SecondsNight -> R.string.entry_error_seconds_night
        LogbookEntryError.SecondsInstrument -> R.string.entry_error_seconds_instrument
        LogbookEntryError.SecondsInstrumentSim -> R.string.entry_error_seconds_instrument_sim
        LogbookEntryError.TakeOffCount -> R.string.entry_error_take_off_count
        LogbookEntryError.Arrival -> R.string.entry_error_arrival_location
        LogbookEntryError.Remarks -> R.string.entry_error_remarks
    }
