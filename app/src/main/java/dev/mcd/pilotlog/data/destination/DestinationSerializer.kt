package dev.mcd.pilotlog.data.destination

import androidx.datastore.core.Serializer
import dev.mcd.pilotlog.data.entry.serializer.Destinations
import dev.mcd.pilotlog.domain.destination.Destination
import java.io.InputStream
import java.io.OutputStream
import dev.mcd.pilotlog.data.entry.serializer.Destination as DestinationProto

object DestinationSerializer : Serializer<Destinations> {
    override val defaultValue: Destinations
        get() = Destinations.getDefaultInstance()

    override fun readFrom(input: InputStream): Destinations {
        return Destinations.parseFrom(input)
    }

    override fun writeTo(t: Destinations, output: OutputStream) {
        t.writeTo(output)
    }
}

val Destination.serialized: DestinationProto
    get() = DestinationProto.newBuilder()
        .setName(name)
        .setIcao(icao)
        .build()

val DestinationProto.toDomain
    get() = Destination(
        name = name,
        icao = icao
    )
