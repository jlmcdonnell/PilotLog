package dev.mcd.pilotlog.ui.newentry.destination

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.destination.Destination
import kotlinx.android.synthetic.main.destination_view.view.*

class DestinationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onSelectDestinationClicked: (() -> Unit)? = null

    init {
        inflate(context, R.layout.destination_view, this)
        backgroundView.setOnClickListener { onSelectDestinationClicked?.invoke() }

        context.obtainStyledAttributes(attrs, R.styleable.DestinationView).use {
            it.getString(R.styleable.DestinationView_destinationLabel).let { label ->
                destinationLabel.text = label
                destinationUnsetLabel.text = label
            }
        }
    }

    fun setDestination(destination: Destination) {
        nameText.text = destination.name
        icaoText.text = destination.icao

        if (destination.name.isNotBlank()) {
            nameText.isVisible = true
            icaoText.isVisible = true
            destinationLabel.isVisible = true
            selectDestination.isVisible = false
            destinationUnsetLabel.isVisible = false
        } else {
            nameText.isVisible = false
            icaoText.isVisible = false
            destinationLabel.isVisible = false
            selectDestination.isVisible = true
            destinationUnsetLabel.isVisible = true
        }
    }
}
