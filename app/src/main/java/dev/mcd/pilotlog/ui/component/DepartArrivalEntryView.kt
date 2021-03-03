package dev.mcd.pilotlog.ui.component

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dev.mcd.pilotlog.R
import kotlinx.android.synthetic.main.depart_arrival_entry_view.view.*
import java.time.LocalTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class DepartArrivalEntryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var onTimesUpdated: (arrival: LocalTime, departure: LocalTime) -> Unit
    lateinit var provideFragmentManager: () -> FragmentManager

    private var arrival: LocalTime? = null
        set(value) {
            field = value
            handleTimeUpdate()
        }

    private var departure: LocalTime? = null
        set(value) {
            field = value
            handleTimeUpdate()
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

        if (!forDeparture && departure != null) {
            minute = departure!!.minute
            hour = departure!!.hour
        }
        MaterialTimePicker.Builder()
            .setMinute(minute)
            .setHour(hour)
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()
            .also { picker ->
                picker.addOnPositiveButtonClickListener {
                    val time = LocalTime.of(picker.hour, picker.minute)

                    if (forDeparture) {
                        departure = time
                    } else {
                        arrival = time
                    }
                }
                picker.showNow(provideFragmentManager(), null)
            }

    }

    private fun handleTimeUpdate() {
        val dtf = DateTimeFormatterBuilder()
            .appendValue(ChronoField.HOUR_OF_DAY)
            .appendLiteral(":")
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .toFormatter()

        arrival?.let {
            arrivalTimeText.text = dtf.format(it)
        }
        departure?.let {
            departureTimeText.text = dtf.format(it)
        }

        if (arrival != null && departure != null) {
            onTimesUpdated(arrival!!, departure!!)
        }
    }
}
