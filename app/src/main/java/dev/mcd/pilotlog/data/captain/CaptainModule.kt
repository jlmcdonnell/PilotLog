package dev.mcd.pilotlog.data.captain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.pilotlog.domain.captain.CaptainRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class CaptainModule {

    @Binds
    abstract fun CaptainRepository(destRepositoryImpl: CaptainRepositoryImpl): CaptainRepository
}
