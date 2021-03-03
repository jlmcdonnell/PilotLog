package dev.mcd.pilotlog.domain.aircraft

data class Aircraft(
    val type: String = "",
    val registration: String = "",
    val engineType: EngineType = EngineType.Single,
)

val Aircraft.isValid get() = type.isNotBlank() && registration.isNotBlank()

enum class EngineType {
    Single,
    Multi
}
