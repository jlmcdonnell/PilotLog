package dev.mcd.pilotlog.ui.draftentry.location

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.location.Location
import kotlinx.android.synthetic.main.location_view.view.*

class LocationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onSelectLocationClicked: (() -> Unit)? = null

    init {
        inflate(context, R.layout.location_view, this)
        backgroundView.setOnClickListener { onSelectLocationClicked?.invoke() }

        context.obtainStyledAttributes(attrs, R.styleable.LocationView).use {
            it.getString(R.styleable.LocationView_locationLabel).let { label ->
                locationLabel.text = label
                locationUnsetLabel.text = label
            }
        }
    }

    fun setLocation(location: Location) {
        nameText.text = location.name
        icaoText.text = location.icao

        if (location.name.isNotBlank()) {
            nameText.isVisible = true
            icaoText.isVisible = true
            locationLabel.isVisible = true
            selectLocation.isVisible = false
            locationUnsetLabel.isVisible = false
        } else {
            nameText.isVisible = false
            icaoText.isVisible = false
            locationLabel.isVisible = false
            selectLocation.isVisible = true
            locationUnsetLabel.isVisible = true
        }
    }
}
