package dev.mcd.pilotlog.data.entry

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import dev.mcd.pilotlog.data.entry.serializer.Entry as EntryProto

object EntrySerializer : Serializer<EntryProto> {
    override val defaultValue: EntryProto
        get() = EntryProto.getDefaultInstance()

    override fun readFrom(input: InputStream): EntryProto {
        return EntryProto.parseFrom(input)
    }

    override fun writeTo(t: EntryProto, output: OutputStream) {
        t.writeTo(output)
    }
}
