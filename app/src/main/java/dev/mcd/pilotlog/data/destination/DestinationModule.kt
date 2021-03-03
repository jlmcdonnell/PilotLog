package dev.mcd.pilotlog.data.destination

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.pilotlog.domain.destination.DestinationRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DestinationModule {

    @Binds
    abstract fun destinationRepository(destRepositoryImpl: DestinationRepositoryImpl): DestinationRepository

    @Binds
    abstract fun destinationDataStore(destinationDataStore: DestinationDataStoreImpl): DestinationDataStore
}
