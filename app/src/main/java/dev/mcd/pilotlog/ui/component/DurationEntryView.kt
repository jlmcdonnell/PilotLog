package dev.mcd.pilotlog.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.util.delegates.EditTextDelegate
import kotlinx.android.synthetic.main.duration_entry_view.view.*
import java.time.Duration

class DurationEntryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onDurationChanged: ((Int) -> Unit)? = null

    private var hours by EditTextDelegate { hoursEditText }
    private var minutes by EditTextDelegate { minutesEditText }
    private var seconds by EditTextDelegate { secondsEditText }

    init {
        inflate(context, R.layout.duration_entry_view, this)

        minutesEditText.doAfterTextChanged {
            formatMinutesSeconds(minutes, minutesEditText)
            updateDuration()
        }
        secondsEditText.doAfterTextChanged {
            formatMinutesSeconds(seconds, secondsEditText)
            updateDuration()
        }
    }

    private fun formatMinutesSeconds(value: String, editText: EditText) {
        if (value.length > 2) {
            editText.setText(value.take(2))
        } else if (value.isNotEmpty()) {
            if (value[0].toString().toIntOrNull() ?: 0 > 5) {
                editText.setText(value.replaceFirst(value[0], '5'))
            }
        }
        editText.setSelection(value.length)
    }

    private fun updateDuration() {
        val hours = hours.toIntOrNull() ?: 0
        val minutes = minutes.toIntOrNull() ?: 0
        val seconds = seconds.toIntOrNull() ?: 0
        val duration = Duration.ofHours(hours.toLong())
            .plus(Duration.ofMinutes(minutes.toLong()))
            .plus(Duration.ofSeconds(seconds.toLong()))

        onDurationChanged?.invoke(duration.seconds.toInt())
    }
}
