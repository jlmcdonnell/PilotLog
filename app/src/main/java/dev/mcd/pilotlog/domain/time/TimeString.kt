package dev.mcd.pilotlog.domain.time

import java.time.LocalTime

/**
 * Time string assumed to be in UTC
 * example: 18:00
 */
typealias TimeString = String

val TimeString?.isValidTime
    get() = runCatching {
        LocalTime.parse(this)
    }.isSuccess
