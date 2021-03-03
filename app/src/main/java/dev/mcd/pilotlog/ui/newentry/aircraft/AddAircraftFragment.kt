package dev.mcd.pilotlog.ui.newentry.aircraft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.domain.aircraft.EngineType
import dev.mcd.pilotlog.domain.aircraft.isValid
import dev.mcd.pilotlog.ui.newentry.aircraft.AddAircraftViewModel.State.Dismiss
import dev.mcd.pilotlog.ui.newentry.aircraft.AddAircraftViewModel.State.ShowUpdateError
import dev.mcd.pilotlog.util.delegates.EditTextDelegate
import dev.mcd.pilotlog.util.extensions.showErrorToast
import kotlinx.android.synthetic.main.add_aircraft_fragment.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AddAircraftFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<AddAircraftViewModel>()

    private var type: String by EditTextDelegate { typeEditText }
    private var registration: String by EditTextDelegate { regEditText }
    private val engineType: EngineType
        get() = when (multiEngineSwitch.isChecked) {
            true -> EngineType.Multi
            false -> EngineType.Single
        }
    private val aircraft get() = Aircraft(type, registration, engineType)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_aircraft_fragment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }

    private fun setupUI() {
        typeEditText.addTextChangedListener { validate() }
        regEditText.addTextChangedListener { validate() }

        addButton.setOnClickListener {
            viewModel.onSaveAircraft(aircraft)
        }
    }

    private fun setupVM() {
        viewModel.state
            .onEach(::updateState)
            .launchIn(lifecycleScope)
    }

    private fun validate() {
        addButton.isEnabled = aircraft.isValid
    }

    private fun updateState(state: AddAircraftViewModel.State) {
        when (state) {
            Dismiss -> findNavController().navigateUp()
            ShowUpdateError -> requireContext().showErrorToast()
        }
    }
}
