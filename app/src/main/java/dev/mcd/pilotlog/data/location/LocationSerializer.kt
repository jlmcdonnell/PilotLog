package dev.mcd.pilotlog.data.location

import androidx.datastore.core.Serializer
import dev.mcd.pilotlog.data.entry.serializer.Locations
import dev.mcd.pilotlog.domain.location.Location
import java.io.InputStream
import java.io.OutputStream
import dev.mcd.pilotlog.data.entry.serializer.Location as LocationProto

object LocationSerializer : Serializer<Locations> {
    override val defaultValue: Locations
        get() = Locations.getDefaultInstance()

    override fun readFrom(input: InputStream): Locations {
        return Locations.parseFrom(input)
    }

    override fun writeTo(t: Locations, output: OutputStream) {
        t.writeTo(output)
    }
}

val Location.serialized: LocationProto
    get() = LocationProto.newBuilder()
        .setName(name)
        .setIcao(icao)
        .build()

val LocationProto.toDomain
    get() = Location(
        name = name,
        icao = icao
    )
