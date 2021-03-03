package dev.mcd.pilotlog.data.aircraft

import androidx.datastore.core.Serializer
import dev.mcd.pilotlog.data.entry.serializer.Aircrafts
import dev.mcd.pilotlog.data.entry.serializer.Aircraft as AircraftProto
import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.domain.aircraft.EngineType
import java.io.InputStream
import java.io.OutputStream

object AircraftSerializer : Serializer<Aircrafts> {
    override val defaultValue: Aircrafts
        get() = Aircrafts.getDefaultInstance()

    override fun readFrom(input: InputStream): Aircrafts {
        return Aircrafts.parseFrom(input)
    }

    override fun writeTo(t: Aircrafts, output: OutputStream) {
        t.writeTo(output)
    }
}

val Aircraft.serialized: AircraftProto
    get() = AircraftProto.newBuilder()
        .setType(type)
        .setRegistration(registration)
        .setIsMulti(engineType == EngineType.Multi)
        .build()

val AircraftProto.toDomain
    get() = Aircraft(
        type = type,
        registration = registration,
        engineType = if (isMulti) EngineType.Multi else EngineType.Single,
    )

