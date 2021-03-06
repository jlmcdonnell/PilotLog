package dev.mcd.pilotlog.data.common.proto

import dev.mcd.pilotlog.data.aircraft.serialized
import dev.mcd.pilotlog.data.aircraft.toDomain
import dev.mcd.pilotlog.data.destination.serialized
import dev.mcd.pilotlog.data.destination.toDomain
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import dev.mcd.pilotlog.data.entry.serializer.LogbookEntry as LogbookEntryProto

val LogbookEntry.serialized: LogbookEntryProto
    get() = LogbookEntryProto.newBuilder()
        .setAircraft(aircraft.serialized)
        .setArrivalTime(arrivalTime)
        .setCaptain(captain)
        .setDate(date)
        .setDepartureTime(departureTime)
        .setFromDestination(fromDestination.serialized)
        .setHoc(holdersOperatingCapacity)
        .setLandingCount(landingCount)
        .setRemarks(remarks)
        .setSecondsDay(secondsDay)
        .setSecondsNight(secondsNight)
        .setSecondsInstrument(secondsInstrument)
        .setSecondsInstrumentSim(secondsInstrumentSim)
        .setTakeOffCount(takeOffCount)
        .setToDestination(toDestination.serialized)
        .build()

val LogbookEntryProto.toDomain
    get() = LogbookEntry(
        aircraft = aircraft.toDomain,
        arrivalTime = arrivalTime,
        captain = captain,
        date = date,
        departureTime = departureTime,
        fromDestination = fromDestination.toDomain,
        holdersOperatingCapacity = hoc,
        landingCount = landingCount,
        remarks = remarks,
        secondsDay = secondsDay,
        secondsNight = secondsNight,
        secondsInstrument = secondsInstrument,
        secondsInstrumentSim = secondsInstrumentSim,
        takeOffCount = takeOffCount,
        toDestination = toDestination.toDomain,
    )
