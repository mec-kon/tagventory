package de.mec_kon.tagventory.first_fragment.adapter

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.InventoryItem
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.single_inventory_list_item.view.*

class InventoryListAdapter(private val items: ArrayList<InventoryItem>, private val context: Activity) :
        RecyclerView.Adapter<InventoryListAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): InventoryListAdapter.ViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.single_inventory_list_item, parent, false)

        return ViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: InventoryListAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: InventoryItem) {
            itemView.inventory_list_name.text = item.name

            ////////// respective tag list //////////

            val inflater = context.layoutInflater

            for (i in 0 until item.tagList.size) {

                // add placeholder textview
                val placeholderTextView = TextView(context)
                placeholderTextView.setPadding(10, 0, 10, 0)

                itemView.inventory_list_tags.addView(placeholderTextView)

                // add textview
                val tagView = inflater.inflate(R.layout.inventory_tag_item, null)
                val xmlTagItem = tagView.findViewById(R.id.tag_item) as TextView
                xmlTagItem.text = items[position].tagList[i].name

                val roundedTagDesignBG = xmlTagItem.background
                roundedTagDesignBG.setColorFilter(items[position].tagList[i].color, PorterDuff.Mode.SRC_ATOP)

                itemView.inventory_list_tags.addView(xmlTagItem)

            }
        }
    }
}



