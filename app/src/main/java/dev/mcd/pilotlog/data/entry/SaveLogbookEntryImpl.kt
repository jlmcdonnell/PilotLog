package dev.mcd.pilotlog.data.entry

import dev.mcd.pilotlog.domain.logbook.LogbookEntryRepository
import dev.mcd.pilotlog.domain.logbook.SaveLogbookEntry
import dev.mcd.pilotlog.domain.logbook.SaveLogbookEntry.Result
import dev.mcd.pilotlog.domain.logbook.validate
import dev.mcd.pilotlog.domain.draftentry.DraftEntryRepository
import javax.inject.Inject

class SaveLogbookEntryImpl @Inject constructor(
    private val logbookEntryRepository: LogbookEntryRepository,
    private val draftEntryRepository: DraftEntryRepository,
) : SaveLogbookEntry {

    override suspend fun execute(): Result {
        val entry = draftEntryRepository.getEntry()
        val logError = entry.validate

        return if (logError!=null) {
            Result.Error(logError)
        } else {
            logbookEntryRepository.save(entry)
            draftEntryRepository.deleteEntry()
            Result.Success
        }
    }
}
