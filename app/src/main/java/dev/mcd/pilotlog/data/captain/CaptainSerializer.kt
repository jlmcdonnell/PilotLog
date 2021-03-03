package dev.mcd.pilotlog.data.captain

import androidx.datastore.core.Serializer
import dev.mcd.pilotlog.data.entry.serializer.Captains
import dev.mcd.pilotlog.domain.captain.Captain
import java.io.InputStream
import java.io.OutputStream
import dev.mcd.pilotlog.data.entry.serializer.Captain as CaptainProto

object CaptainSerializer : Serializer<Captains> {
    override val defaultValue: Captains
        get() = Captains.getDefaultInstance()

    override fun readFrom(input: InputStream): Captains {
        return Captains.parseFrom(input)
    }

    override fun writeTo(t: Captains, output: OutputStream) {
        t.writeTo(output)
    }
}

val Captains.toDomain get() = captainsList.map { Captain(it.name) }

val Captain.serializer: CaptainProto
    get() = CaptainProto
        .newBuilder()
        .setName(name)
        .build()
