package dev.mcd.pilotlog.ui.newentry.destination

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.destination.Destination
import kotlinx.android.synthetic.main.destination_item_view.*

class DestinationListItem(val destination: Destination) : Item() {
    override fun getLayout(): Int = R.layout.destination_item_view

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.icao.text = destination.icao
        viewHolder.name.text = destination.name
    }
}
