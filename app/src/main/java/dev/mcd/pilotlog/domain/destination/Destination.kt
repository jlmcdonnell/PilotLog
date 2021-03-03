package dev.mcd.pilotlog.domain.destination

data class Destination(
    val name: String = "",
    val icao: String = ""
)

val Destination.isValid get() = name.isNotBlank()
