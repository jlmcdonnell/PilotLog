package dev.mcd.pilotlog.domain.time

import java.time.LocalDateTime

/**
 * ISO-8601 date string
 * example: 2021-03-06
 */
typealias DateString = String

val DateString.isValidDate
    get() = runCatching {
        LocalDateTime.parse(this)
    }.isSuccess
