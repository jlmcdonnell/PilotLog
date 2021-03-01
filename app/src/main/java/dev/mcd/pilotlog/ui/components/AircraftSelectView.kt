package dev.mcd.pilotlog.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.databinding.AircraftSelectViewBinding
import dev.mcd.pilotlog.domain.entry.Aircraft
import dev.mcd.pilotlog.domain.entry.EngineType
import dev.mcd.pilotlog.util.delegates.EditTextDelegate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class AircraftSelectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var type: String by EditTextDelegate { binding.typeEditText }
    private var registration: String by EditTextDelegate { binding.regEditText }
    private var binding: AircraftSelectViewBinding
    private val _aircraft = MutableSharedFlow<Aircraft>()

    val aircraft: SharedFlow<Aircraft> get() = _aircraft

    init {
        inflate(context, R.layout.aircraft_select_view, this)
        binding = AircraftSelectViewBinding.bind(this)
        with(binding) {
            typeEditText.addTextChangedListener { updateAircraft() }
            regEditText.addTextChangedListener { updateAircraft() }
        }
        if (isInEditMode) {
            type = "PA28"
            registration = "G-BRDF"
        }
    }

    private fun updateAircraft() {
        val engineType = when (binding.multiEngineSwitch.isChecked) {
            true -> EngineType.Multi
            false -> EngineType.Single
        }
        _aircraft.tryEmit(Aircraft(type, registration, engineType))
    }

}
