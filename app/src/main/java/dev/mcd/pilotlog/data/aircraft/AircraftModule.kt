package dev.mcd.pilotlog.data.aircraft

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.pilotlog.domain.aircraft.AircraftRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AircraftModule {
    @Binds
    abstract fun aircraftRepository(aircraftRepository: AircraftRepositoryImpl): AircraftRepository
}
