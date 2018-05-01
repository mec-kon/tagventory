package de.mec_kon.tagventory

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView


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


            val names = arrayListOf<String>()
            val tags = arrayListOf<ArrayList<String>>()
            for (i in items) {
                names.add(i.name)
                tags.add(i.tagList)
            }


            ////////// respective item name //////////

            xmlInventoryListName.text = names[position]


            ////////// respective tag list //////////

            for (i in 0 until tags[position].size) {

                // add placeholder textview
                val placeholderTextView = TextView(context)
                placeholderTextView.setPadding(10,0,10,0)
                xmlInventoryListTags.addView(placeholderTextView)

                // add textview
                val tagView = inflater.inflate(R.layout.inventory_tag_item, null)
                val xmlTagItem = tagView.findViewById(R.id.tag_item) as TextView
                xmlTagItem.text = tags[position][i]
                xmlInventoryListTags.addView(xmlTagItem)
            }

            return innerView
        }
    }
}



