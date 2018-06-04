package de.mec_kon.tagventory.first_fragment.adapter

import android.app.Activity
import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.InventoryItem
import kotlinx.android.synthetic.main.single_inventory_list_item.view.*

class InventoryListAdapter(private val items: ArrayList<InventoryItem>, private val context: Activity) :
        RecyclerView.Adapter<InventoryListAdapter.ViewHolder>() {

    // declares an interface that delegates handling of onClickListener-events
    interface InventoryListInterface {
        fun onClickInvoked(position: Int)
        fun onLongClickInvoked(position: Int)
    }

    // determines the class instance that implements the InventoryListInterface
    private lateinit var inventoryListInterfaceImplementer: InventoryListInterface
    fun setInventoryListInterfaceImplementer(interfaceImplementer: InventoryListInterface) {
        this.inventoryListInterfaceImplementer = interfaceImplementer
    }



    // recyclerView process

    // create new views (invoked by the layout manager) and set their onClickListeners
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.single_inventory_list_item, parent, false)

        val viewHold = ViewHolder(itemView)

        itemView.setOnLongClickListener({
            inventoryListInterfaceImplementer.onLongClickInvoked(viewHold.adapterPosition)

            // means that the event has been handled and will not invoke any other onClickListeners
            true
        })

        itemView.setOnClickListener({
            inventoryListInterfaceImplementer.onClickInvoked(viewHold.adapterPosition)
        })

        return viewHold
    }

    // replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: InventoryListAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    // return the size of the data set (invoked by the layout manager)
    override fun getItemCount() = items.size



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // how to replace the contents of a view
        fun bindItems(item: InventoryItem) {
            val inflater = context.layoutInflater

            // set item name
            itemView.inventory_list_name.text = item.name

            // prevent the tag lists to be corrupted by the re-adding of tags in case the ViewHolder is created again
            itemView.inventory_list_tags.removeAllViews()

            // set item tag list
            for (i in 0 until item.tagList.size) {

                // add placeholder textView

                val placeholderTextView = TextView(context)
                placeholderTextView.setPadding(10, 0, 10, 0)

                itemView.inventory_list_tags.addView(placeholderTextView)

                // add tag name

                val tagView = inflater.inflate(R.layout.inventory_tag_item, null)
                val xmlTagItem = tagView.findViewById(R.id.tag_item) as TextView
                xmlTagItem.text = items[position].tagList[i].name

                // set tag color

                val roundedTagDesignBG = xmlTagItem.background
                // SRC_ATOP makes the colorFilter overlay the xmlTagItem's background (rounded_tag_design.xml)
                roundedTagDesignBG.setColorFilter(items[position].tagList[i].color, PorterDuff.Mode.SRC_ATOP)

                itemView.inventory_list_tags.addView(xmlTagItem)
            }

        }
    }
}



