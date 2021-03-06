package dev.mcd.pilotlog.data.captain

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.mcd.pilotlog.domain.captain.Captain
import dev.mcd.pilotlog.domain.captain.CaptainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.captainDataStore by dataStore("captain.pb", CaptainSerializer)

class CaptainRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : CaptainRepository {

    private val dataStore = context.captainDataStore

    override fun get(): Flow<List<Captain>> {
        return dataStore.data.map { it.toDomain }
    }

    override suspend fun save(captain: Captain) {
        dataStore.updateData { captains ->
            return@updateData if (captains.captainsList.none { c -> Captain(c.name)==captain }) {
                captains.toBuilder()
                    .addCaptains(captain.serializer)
                    .build()
            } else captains
        }
    }
}
