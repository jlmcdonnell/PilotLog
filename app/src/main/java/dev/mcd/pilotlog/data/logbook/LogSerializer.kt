package dev.mcd.pilotlog.data.logbook

import androidx.datastore.core.Serializer
import dev.mcd.pilotlog.data.entry.serializer.Logbook
import java.io.InputStream
import java.io.OutputStream

object LogSerializer : Serializer<Logbook> {
    override val defaultValue: Logbook
        get() = Logbook.getDefaultInstance()

    override fun readFrom(input: InputStream): Logbook {
        return Logbook.parseFrom(input)
    }

    override fun writeTo(t: Logbook, output: OutputStream) {
        t.writeTo(output)
    }
}
