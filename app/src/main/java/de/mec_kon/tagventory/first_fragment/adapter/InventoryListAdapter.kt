package de.mec_kon.tagventory.first_fragment.adapter

import android.app.Activity
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.InventoryItem


class InventoryListAdapter(context: Activity, items: ArrayList<InventoryItem>) {

    private val names = arrayListOf<String>()
    val innerAdapter: InnerInventoryListAdapter

    init {
        for (i in items) {
            names.add(i.name)
        }
        innerAdapter = InnerInventoryListAdapter(context, items)

    }

    inner class InnerInventoryListAdapter(private val context: Activity, private val items: ArrayList<InventoryItem>) : ArrayAdapter<String>(context, R.layout.single_inventory_list_item, names) {

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            val inflater = context.layoutInflater
            val innerView = inflater.inflate(R.layout.single_inventory_list_item, null, true)
            val xmlInventoryListName = innerView.findViewById<View>(R.id.inventory_list_name) as TextView
            val xmlInventoryListTags = innerView.findViewById<View>(R.id.inventory_list_tags) as LinearLayout


            ////////// respective item name //////////

            xmlInventoryListName.text = items[position].name


            ////////// respective tag list //////////

            for (i in 0 until items[position].tagList.size) {

                // add placeholder textview
                val placeholderTextView = TextView(context)
                placeholderTextView.setPadding(10,0,10,0)
                xmlInventoryListTags.addView(placeholderTextView)

                // add textview
                val tagView = inflater.inflate(R.layout.inventory_tag_item, null)
                val xmlTagItem = tagView.findViewById(R.id.tag_item) as TextView
                xmlTagItem.text = items[position].tagList[i].name

                val roundedTagDesignBG = xmlTagItem.background
                roundedTagDesignBG.setColorFilter(items[position].tagList[i].color, PorterDuff.Mode.SRC_ATOP)

                xmlInventoryListTags.addView(xmlTagItem)
            }

            return innerView
        }
    }
}



