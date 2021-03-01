package dev.mcd.pilotlog.ui.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.databinding.AddDestinationFragmentBinding
import dev.mcd.pilotlog.domain.entry.Destination
import dev.mcd.pilotlog.util.delegates.EditTextDelegate
import dev.mcd.pilotlog.util.extensions.setNavigationResult
import java.io.Serializable

class AddDestinationFragment : BottomSheetDialogFragment() {

    data class Result(val destination: Destination) : Serializable

    private lateinit var binding: AddDestinationFragmentBinding

    private var name by EditTextDelegate { binding.nameEditText }
    private var icao by EditTextDelegate { binding.icaoEditText }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_destination_fragment, container).also {
            binding = AddDestinationFragmentBinding.bind(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            nameEditText.addTextChangedListener {
                validate()
            }
            icaoEditText.addTextChangedListener {
                validate()
            }
            addButton.setOnClickListener {
                setNavigationResult(Result(Destination(name, icao)))
                dismiss()
            }
        }
    }

    private fun validate() {
        binding.addButton.isEnabled = name.isNotBlank()
    }
}
