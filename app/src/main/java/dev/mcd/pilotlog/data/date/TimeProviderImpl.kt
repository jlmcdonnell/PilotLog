package dev.mcd.pilotlog.data.date

import dev.mcd.pilotlog.domain.date.TimeProvider
import java.time.LocalDate

class TimeProviderImpl : TimeProvider {
    override val now: Long
        get() = System.currentTimeMillis()
    override val dateString: String
        get() = LocalDate.now().toString()
}
