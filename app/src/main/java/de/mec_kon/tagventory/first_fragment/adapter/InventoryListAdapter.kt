package de.mec_kon.tagventory.first_fragment.adapter

import android.app.Activity
import android.graphics.PorterDuff
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.InventoryItem
import kotlinx.android.synthetic.main.single_inventory_list_item.view.*

/**
 * Adapter for the inventory item list.
 *
 * This class is used to bind the resultingItemList.
 *
 * @property items the list of items that is bound to the corresponding RecyclerView
 * @property context the context that is passed to the tag list's LinearLayoutManager
 */
class InventoryListAdapter(private val items: ArrayList<InventoryItem>, private val context: Activity) :
        RecyclerView.Adapter<InventoryListAdapter.ViewHolder>() {

    //////////////////// interfaces ////////////////////
    /**
     * Interface for the inventory item list.
     *
     * This interface is mainly used to delegate onClick events to the corresponding fragment.
     */
    interface InventoryListInterface {
        fun onClickInvoked(itemToBeChanged: InventoryItem)
        fun onLongClickInvoked(itemToBeChanged: InventoryItem)
        fun onItemAddTag(itemToBeChanged: InventoryItem, tagName: String)
    }

    // determines the class instance that implements the InventoryListInterface
    private lateinit var inventoryListInterfaceImplementer: InventoryListInterface

    /**
     * Determines the implementing instance for the interface.
     *
     * This is set by the corresponding class instance to implement the InventoryListInterface functions.
     * @param interfaceImplementer the class instance, that implements the interface
     */
    fun setInventoryListInterfaceImplementer(interfaceImplementer: InventoryListInterface) {
        this.inventoryListInterfaceImplementer = interfaceImplementer
    }



    //////////////////// actual class implementation ////////////////////
    val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.single_inventory_list_item, parent, false)
        val viewHold = ViewHolder(itemView)

        // set OnLongClickListener for the single_inventory_list_item
        itemView.setOnLongClickListener {
            inventoryListInterfaceImplementer.onLongClickInvoked(items[viewHold.adapterPosition])

            // means that the event has been handled
            true
        }

        // set OnEditorActionListener for the item_add_tag_input
        itemView.item_add_tag_input.setOnEditorActionListener { _, _, _ ->
            // get input from EditText
            val newTagName = itemView.item_add_tag_input.text.toString()

            if (newTagName != "") {
                inventoryListInterfaceImplementer.onItemAddTag(items[viewHold.adapterPosition], newTagName)
            }

            // means that the event has been handled
            true
        }

        return viewHold
    }



    /**
     * ViewHolder for the InventoryListAdapter.
     *
     * This class is used to bind an inventory item to its view.
     *
     * @property itemView the view that the item is bound to
     * @see TagListAdapter for the tag list within a single inventory item
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var recyclerView: RecyclerView
        private lateinit var viewAdapter: RecyclerView.Adapter<*>
        private lateinit var viewManager: RecyclerView.LayoutManager

        fun bindItems(item: InventoryItem) {

            // set the inventory_list_name to the corresponding item's name
            itemView.inventory_list_name.text = item.name

            // clear the item_add_tag_input
            itemView.item_add_tag_input.text.clear()

            // set item_add_tag_input color
            itemView.item_add_tag_input.background.setColorFilter(0xE6E6E6, PorterDuff.Mode.SRC_ATOP)

            // make the tag list display the corresponding item's tags
            viewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            viewAdapter = TagListAdapter(item.tagList)
            recyclerView = itemView.findViewById<RecyclerView>(R.id.inventory_list_tags).apply {
                // setting to improve performance when layout size of the inventory item list stays fixed
                setHasFixedSize(true)

                layoutManager = viewManager
                adapter = viewAdapter
            }

            recyclerView.recycledViewPool = viewPool
        }

    }



}



