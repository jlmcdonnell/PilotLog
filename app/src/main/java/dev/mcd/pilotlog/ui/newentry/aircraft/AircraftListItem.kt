package dev.mcd.pilotlog.ui.newentry.aircraft

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.domain.aircraft.EngineType
import kotlinx.android.synthetic.main.aircraft_item_view.*

class AircraftListItem(val aircraft: Aircraft) : Item() {
    override fun getLayout(): Int = R.layout.aircraft_item_view

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.registration.text = aircraft.registration
        viewHolder.type.text = aircraft.type

        val engineType = when (aircraft.engineType) {
            EngineType.Single -> R.string.engine_type_single
            EngineType.Multi -> R.string.engine_type_multi
        }
        viewHolder.engineType.setText(engineType)
    }
}
