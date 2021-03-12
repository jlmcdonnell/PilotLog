package dev.mcd.pilotlog.domain.location

data class Location(
    val name: String = "",
    val icao: String = ""
)

val Location.isValid get() = name.isNotBlank()
