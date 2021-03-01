package dev.mcd.pilotlog.domain.date

interface TimeProvider {
    val now: Long
    val dateString: String
}
