package dev.mcd.pilotlog.ui.component

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.time.TimeProvider
import dev.mcd.pilotlog.domain.time.TimeString
import dev.mcd.pilotlog.domain.time.isValidTime
import kotlinx.android.synthetic.main.depart_arrival_entry_view.view.*
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class DepartArrivalEntryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @Inject
    lateinit var timeProvider: TimeProvider

    lateinit var onTimesUpdated: (departure: TimeString, arrival: TimeString) -> Unit
    lateinit var provideFragmentManager: () -> FragmentManager

    var arrival: TimeString = ""
        set(value) {
            field = value
            arrivalTimeText.text = field
        }

    var departure: TimeString = ""
        set(value) {
            field = value
            departureTimeText.text = field
        }

    init {
        inflate(context, R.layout.depart_arrival_entry_view, this)

        departureView.setOnClickListener {
            showTimePicker(forDeparture = true)
        }
        arrivalView.setOnClickListener {
            showTimePicker(forDeparture = false)
        }
    }

    private fun showTimePicker(forDeparture: Boolean) {
        var minute = 0
        var hour = 0

        if (!forDeparture && departure.isValidTime) {
            val departureTime = timeProvider.parseTime(departure!!)
            minute = departureTime.minute
            hour = departureTime.hour
        }
        MaterialTimePicker.Builder()
            .setMinute(minute)
            .setHour(hour)
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    val time = LocalTime.of(this.hour, this.minute)
                    val timeString = timeProvider.formatTime(time)

                    if (forDeparture) {
                        departure = timeString
                    } else {
                        arrival = timeString
                    }
                    onTimesUpdated(departure, arrival)
                }
                showNow(provideFragmentManager(), null)
            }
    }
}
