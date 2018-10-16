package de.mec_kon.tagventory.first_fragment.adapter

import android.app.Activity
import android.content.ClipData
import android.graphics.PorterDuff
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.InventoryItem
import de.mec_kon.tagventory.first_fragment.datastructure.Tag
import kotlinx.android.synthetic.main.inventory_tag_item.view.*
import kotlinx.android.synthetic.main.single_inventory_list_item.view.*
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.graphics.Canvas
import android.graphics.Point
import android.widget.TextView


class InventoryListAdapter(private val items: ArrayList<InventoryItem>, private val context: Activity) :
        RecyclerView.Adapter<InventoryListAdapter.ViewHolder>() {

    // declares an interface that delegates handling of onClickListener-events
    interface InventoryListInterface {
        fun onClickInvoked(position: Int)
        fun onLongClickInvoked(position: Int)
        fun onItemAddTag(position: Int, tagName: String)
    }

    // determines the class instance that implements the InventoryListInterface
    private lateinit var inventoryListInterfaceImplementer: InventoryListInterface

    fun setInventoryListInterfaceImplementer(interfaceImplementer: InventoryListInterface) {
        this.inventoryListInterfaceImplementer = interfaceImplementer
    }

    val viewPool = RecyclerView.RecycledViewPool()

    // recyclerView process

    // create new views (invoked by the layout manager) and set their onClickListeners
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.single_inventory_list_item, parent, false)

        val viewHold = ViewHolder(itemView)
/*
        itemView.setOnLongClickListener({
            inventoryListInterfaceImplementer.onLongClickInvoked(viewHold.adapterPosition)

            // means that the event has been handled and will not invoke any other onClickListeners
            true
        })

        itemView.setOnClickListener({
            inventoryListInterfaceImplementer.onClickInvoked(viewHold.adapterPosition)
        })

        // set OnEditorActionListener for the item_add_tag_input

        itemView.item_add_tag_input.setOnEditorActionListener { _, _, _ ->

            // get input from EditText
            val newTagName = itemView.item_add_tag_input.text.toString()

            if (newTagName != "") {
                inventoryListInterfaceImplementer.onItemAddTag(viewHold.adapterPosition, newTagName)
            }

            // means that the event has been handled
            true
        }
*/
        return viewHold
    }

    // replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: InventoryListAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    // return the size of the data set (invoked by the layout manager)
    override fun getItemCount() = items.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var recyclerView: RecyclerView
        private lateinit var viewAdapter: RecyclerView.Adapter<*>
        private lateinit var viewManager: RecyclerView.LayoutManager

        // how to replace the contents of a view
        fun bindItems(item: InventoryItem) {

            // set item name
            itemView.inventory_list_name.text = item.name

            // clear the item_add_tag_input
            itemView.item_add_tag_input.text.clear()


            viewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            viewAdapter = TagListAdapter(item.tagList, context)


            recyclerView = itemView.findViewById<RecyclerView>(R.id.inventory_list_tags).apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)

                // use a linear layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter

            }
            recyclerView.recycledViewPool = viewPool

        }

        fun bindTags(tag: Tag) {

            itemView.tag_item.text = tag.name


            // set tag color
            val roundedTagDesignBG = itemView.tag_item.background
            // SRC_ATOP makes the colorFilter overlay the xmlTagItem's background (rounded_tag_design.xml)
            roundedTagDesignBG.setColorFilter(tag.color, PorterDuff.Mode.SRC_ATOP)


        }

    }


    inner class TagListAdapter(private val tagList: ArrayList<Tag>, private val context: Activity) :
            RecyclerView.Adapter<InventoryListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.inventory_tag_item, parent, false)

            val viewHold = ViewHolder(itemView)


            itemView.setOnLongClickListener({

                val type: Array<String> = Array(1, {_ -> MIMETYPE_TEXT_PLAIN})
                val tagItem: ClipData.Item = ClipData.Item("")
                val tagDragData = ClipData("Tag", type, tagItem)

                val tagShadow = TagDragShadowBuilder(itemView)
                val tag = Tag(tagList[viewHold.adapterPosition].name, (tagList[viewHold.adapterPosition].color))

                itemView.startDragAndDrop(
                        tagDragData,                // the data to be dragged
                        tagShadow,                  // the drag shadow builder
                        tag,                        // the object to be passed
                        0                     // flags (not used: set to 0)
                )

                true
            })


            return viewHold
        }


        override fun onBindViewHolder(holder: InventoryListAdapter.ViewHolder, position: Int) {
           holder.bindTags(tagList[position])
        }

        override fun getItemCount(): Int {
           return  tagList.size
        }


        inner class TagDragShadowBuilder(v: View) : View.DragShadowBuilder(v) {

            private val shadow: TextView = v.tag_item

            override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {
                super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint)

                val width = shadow.width
                val height = shadow.height

                // shadow size ?

                outShadowSize!!.set(width, height)

                outShadowTouchPoint!!.set((width/4) * 3, height)
            }

            override fun onDrawShadow(canvas: Canvas) {
                // Draws the ColorDrawable in the Canvas passed in from the system.
                shadow.draw(canvas)
            }

        }

    }

}



