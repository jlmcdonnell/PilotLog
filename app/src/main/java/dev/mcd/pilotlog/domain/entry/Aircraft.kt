package dev.mcd.pilotlog.domain.entry

data class Aircraft(
    val type: String,
    val registration: String,
    val engineType: EngineType,
)

enum class EngineType {
    Single,
    Multi
}
