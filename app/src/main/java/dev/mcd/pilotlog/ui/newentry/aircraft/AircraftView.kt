package dev.mcd.pilotlog.ui.newentry.aircraft

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.domain.aircraft.EngineType
import dev.mcd.pilotlog.domain.aircraft.isValid
import kotlinx.android.synthetic.main.aircraft_view.view.*

class AircraftView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.aircraft_view, this)
    }

    var aircraft: Aircraft = Aircraft()
        set(value) {
            field = value
            updateAircraft()
        }

    private fun updateAircraft() {
        if (aircraft.isValid) {
            registrationText.text = aircraft.registration
            typeText.text = aircraft.type
            typeText.isVisible = aircraft.type.isNotBlank()

            val engineType = when (aircraft.engineType) {
                EngineType.Single -> R.string.engine_type_single
                EngineType.Multi -> R.string.engine_type_multi
            }
            engineTypeText.text = context.getString(engineType)
        } else {
            registrationText.text = ""
            typeText.text = ""
            engineTypeText.text = ""
        }
    }
}
