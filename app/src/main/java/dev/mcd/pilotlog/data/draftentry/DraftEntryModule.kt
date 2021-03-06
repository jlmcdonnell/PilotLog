package dev.mcd.pilotlog.data.draftentry

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.pilotlog.domain.draftentry.DraftEntryRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DraftEntryModule {

    @Binds
    abstract fun draftEntryRepository(draftEntryRepositoryImpl: DraftEntryRepositoryImpl): DraftEntryRepository

    @Binds
    abstract fun draftEntryDataStore(draftEntryDataStore: DraftEntryDataStoreImpl): DraftEntryDataStore
}
