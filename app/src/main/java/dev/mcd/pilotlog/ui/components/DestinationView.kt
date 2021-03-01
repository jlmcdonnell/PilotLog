package dev.mcd.pilotlog.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.databinding.DestinationViewBinding
import dev.mcd.pilotlog.domain.entry.Destination

class DestinationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: DestinationViewBinding

    var onSelectDestinationClicked: (() -> Unit)? = null

    init {
        inflate(context, R.layout.destination_view, this)
        binding = DestinationViewBinding.bind(this)
        binding.root.setOnClickListener { onSelectDestinationClicked?.invoke() }

        context.obtainStyledAttributes(attrs, R.styleable.DestinationView).use {
            binding.destinationLabel.text = it.getString(R.styleable.DestinationView_destinationLabel)
        }
    }

    fun setDestination(destination: Destination) {
        with(binding) {
            nameText.isVisible = true
            icaoText.isVisible = true
            selectDestination.isVisible = false
            nameText.text = destination.name
            icaoText.text = destination.icao
        }
    }

}
