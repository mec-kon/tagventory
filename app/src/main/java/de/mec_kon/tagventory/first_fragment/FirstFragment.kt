package de.mec_kon.tagventory.first_fragment

import android.app.Fragment
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast
import android.widget.ExpandableListView.OnGroupCollapseListener
import android.widget.ExpandableListView.OnGroupExpandListener
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.adapter.FilterExpanderAdapter
import de.mec_kon.tagventory.first_fragment.adapter.InventoryListAdapter
import de.mec_kon.tagventory.first_fragment.datastructure.Filter
import de.mec_kon.tagventory.first_fragment.datastructure.InventoryItem
import de.mec_kon.tagventory.first_fragment.datastructure.Tag


class FirstFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_first, container, false)


        ////////// create inventory list //////////
        val xmlInventoryList = view.findViewById<View>(R.id.inventory_list) as ListView

        val tag1 = Tag("One", rgb(255, 0, 20))
        val tag2 = Tag("Two", rgb(20, 0, 255))
        val tag3 = Tag("Three", rgb(0, 50, 100))
        val tag4 = Tag("Four", rgb(90, 90, 90))
        val tag5 = Tag("Five", rgb(200, 50, 20))

        val tagList1 = arrayListOf<Tag>(tag1, tag2, tag3, tag4, tag5)
        val tagList2 = arrayListOf<Tag>(tag2, tag4)
        val tagList3 = arrayListOf<Tag>(tag1, tag2, tag5, tag4, tag1, tag2, tag5, tag4, tag1, tag2, tag5, tag4)

        val item1 = InventoryItem("test", 1, tagList1)
        val item2 = InventoryItem("test2", 1, tagList2)
        val item3 = InventoryItem("test3", 1, tagList3)
        val item4 = InventoryItem("test4", 1, tagList1)
        val item5 = InventoryItem("test5", 1, tagList2)
        val item6 = InventoryItem("test6", 1, tagList3)
        val item7 = InventoryItem("test7", 1, tagList1)
        val item8 = InventoryItem("test8", 1, tagList2)
        val item9 = InventoryItem("test9", 1, tagList3)

        val list = arrayListOf<InventoryItem>(item1, item2, item3, item4, item5, item6, item7, item8, item9)

        val inventoryAdapter = InventoryListAdapter(activity, list)
        xmlInventoryList.adapter = inventoryAdapter.innerAdapter


        ////////// create filter element //////////
        val header = arrayListOf("")
        val children = arrayListOf("1", "2")
        val filterHashMap = hashMapOf(header[0] to children)

        val filter1 = Filter(tagList1, tagList2)

        val filterAdapter = FilterExpanderAdapter(activity, header, filterHashMap, filter1)
        val xmlFilterElement: ExpandableListView?
        xmlFilterElement = view.findViewById(R.id.filter_element) as ExpandableListView
        xmlFilterElement.setAdapter(filterAdapter)




        // Listview Group expanded listener
        xmlFilterElement.setOnGroupExpandListener(OnGroupExpandListener {
            Toast.makeText(activity, "Es funktioniert! Expandiert!", Toast.LENGTH_SHORT).show()
        })

        // Listview Group collapsed listener
        xmlFilterElement.setOnGroupCollapseListener(OnGroupCollapseListener {
            Toast.makeText(activity, "Es funktioniert! Kollabiert!", Toast.LENGTH_SHORT).show()
        })




        return view
    }



}