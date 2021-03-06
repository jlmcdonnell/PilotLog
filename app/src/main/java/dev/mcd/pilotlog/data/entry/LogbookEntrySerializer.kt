package dev.mcd.pilotlog.data.entry

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import dev.mcd.pilotlog.data.entry.serializer.LogbookEntry as LogbookEntryProto

object LogbookEntrySerializer : Serializer<LogbookEntryProto> {
    override val defaultValue: LogbookEntryProto
        get() = LogbookEntryProto.getDefaultInstance()

    override fun readFrom(input: InputStream): LogbookEntryProto {
        return LogbookEntryProto.parseFrom(input)
    }

    override fun writeTo(t: LogbookEntryProto, output: OutputStream) {
        t.writeTo(output)
    }
}
