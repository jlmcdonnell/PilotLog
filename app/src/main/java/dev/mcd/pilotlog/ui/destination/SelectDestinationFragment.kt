package dev.mcd.pilotlog.ui.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.databinding.SelectDestinationFragmentBinding
import dev.mcd.pilotlog.domain.entry.Destination
import dev.mcd.pilotlog.util.extensions.getNavigationResultLiveData
import dev.mcd.pilotlog.util.extensions.setNavigationResult
import java.io.Serializable

data class SelectDestinationParams(
    val tag: String,
) : Serializable

@AndroidEntryPoint
class SelectDestinationFragment : BottomSheetDialogFragment() {

    data class Result(
        val tag: String,
        val destination: Destination,
    )

    private lateinit var binding: SelectDestinationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_destination_fragment, container).also {
            binding = SelectDestinationFragmentBinding.bind(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newDestination.setOnClickListener {
            navigateToAddDestination()
        }
    }

    private fun navigateToAddDestination() {
        findNavController().navigate(SelectDestinationFragmentDirections.selectDestinationToAddDestination())
        observeAddDestinationResults()
    }

    private fun observeAddDestinationResults() {
        getNavigationResultLiveData<AddDestinationFragment.Result>()?.observe(viewLifecycleOwner) {
            dismissWithDestination(it.destination)
        }
    }

    private fun dismissWithDestination(destination: Destination) {
        val tag = navArgs<SelectDestinationFragmentArgs>().value.params.tag
        val result = Result(tag, destination)
        setNavigationResult(result, key = "1")
        dismiss()
    }
}
