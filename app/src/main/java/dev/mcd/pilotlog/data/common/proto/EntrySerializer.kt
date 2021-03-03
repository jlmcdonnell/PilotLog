package dev.mcd.pilotlog.data.common.proto

import dev.mcd.pilotlog.data.aircraft.serialized
import dev.mcd.pilotlog.data.aircraft.toDomain
import dev.mcd.pilotlog.data.destination.serialized
import dev.mcd.pilotlog.data.destination.toDomain
import dev.mcd.pilotlog.domain.entry.Entry
import dev.mcd.pilotlog.data.entry.serializer.Entry as EntryProto

val Entry.serialized: EntryProto
    get() = EntryProto.newBuilder()
        .setDate(date)
        .setAircraft(aircraft.serialized)
        .setCaptain(captain)
        .setHoc(holdersOperatingCapacity)
        .setFromDestination(fromDestination.serialized)
        .setToDestination(toDestination.serialized)
        .build()

val EntryProto.toDomain
    get() = Entry(
        date = date,
        captain = captain,
        holdersOperatingCapacity = hoc,
        aircraft = aircraft.toDomain,
        fromDestination = fromDestination.toDomain,
        toDestination = toDestination.toDomain,
    )
