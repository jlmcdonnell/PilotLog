package dev.mcd.pilotlog.data.newentry

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.pilotlog.domain.newentry.NewEntryRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class NewEntryModule {

    @Binds
    abstract fun newEntryRepository(newEntryRepositoryImpl: NewEntryRepositoryImpl): NewEntryRepository

    @Binds
    abstract fun newEntryDataStore(newEntryDataStore: NewEntryDataStoreImpl): NewEntryDataStore
}
