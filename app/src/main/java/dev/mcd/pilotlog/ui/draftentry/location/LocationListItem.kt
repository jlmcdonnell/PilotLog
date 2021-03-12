package dev.mcd.pilotlog.ui.draftentry.location

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.location.Location
import kotlinx.android.synthetic.main.location_item_view.*

class LocationListItem(val location: Location) : Item() {
    override fun getLayout(): Int = R.layout.location_item_view

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.icao.text = location.icao
        viewHolder.name.text = location.name
    }
}
