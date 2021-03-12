package dev.mcd.pilotlog.data.common.proto

import dev.mcd.pilotlog.data.aircraft.serialized
import dev.mcd.pilotlog.data.aircraft.toDomain
import dev.mcd.pilotlog.data.location.serialized
import dev.mcd.pilotlog.data.location.toDomain
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import dev.mcd.pilotlog.data.entry.serializer.LogbookEntry as LogbookEntryProto

val LogbookEntry.serialized: LogbookEntryProto
    get() = LogbookEntryProto.newBuilder()
        .setAircraft(aircraft.serialized)
        .setArrivalTime(arrivalTime)
        .setCaptain(captain)
        .setDate(date)
        .setDepartureTime(departureTime)
        .setDeparture(departure.serialized)
        .setHoc(holdersOperatingCapacity)
        .setLandingCount(landingCount)
        .setRemarks(remarks)
        .setSecondsDay(secondsDay)
        .setSecondsNight(secondsNight)
        .setSecondsInstrument(secondsInstrument)
        .setSecondsInstrumentSim(secondsInstrumentSim)
        .setTakeOffCount(takeOffCount)
        .setArrival(arrival.serialized)
        .build()

val LogbookEntryProto.toDomain
    get() = LogbookEntry(
        aircraft = aircraft.toDomain,
        arrivalTime = arrivalTime,
        captain = captain,
        date = date,
        departureTime = departureTime,
        departure = departure.toDomain,
        holdersOperatingCapacity = hoc,
        landingCount = landingCount,
        remarks = remarks,
        secondsDay = secondsDay,
        secondsNight = secondsNight,
        secondsInstrument = secondsInstrument,
        secondsInstrumentSim = secondsInstrumentSim,
        takeOffCount = takeOffCount,
        arrival = arrival.toDomain,
    )
