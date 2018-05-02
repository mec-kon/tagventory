package de.mec_kon.tagventory

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast
import android.widget.ExpandableListView.OnGroupCollapseListener
import android.widget.ExpandableListView.OnGroupExpandListener




class FirstFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_first, container, false)


        ////////// create inventory list //////////
        val xmlInventoryList = view.findViewById<View>(R.id.inventory_list) as ListView

        val item = InventoryItem("test", arrayListOf("tag1", "tag2", "tag3", "tag4", "tag5", "tag1", "tag2", "tag3", "tag4", "tag5", "tag1", "tag2", "tag3", "tag4", "tag5"))
        val item2 = InventoryItem("test2", arrayListOf("one", "two", "three"))
        val item3 = InventoryItem("test3", arrayListOf("eins", "zwei", "drei", "vier", "fuenf", "sechs"))
        val list = arrayListOf<InventoryItem>(item, item2, item3)

        val inventoryAdapter = InventoryListAdapter(activity, list)
        xmlInventoryList.adapter = inventoryAdapter.innerAdapter


        ////////// create filter element //////////
        val header = arrayListOf("")
        val children = arrayListOf("1", "2")
        val filterHashMap = hashMapOf(header[0] to children)
        val tagsReq = arrayListOf<String>("bla", "ble", "bli", "blo", "blu", "der", "Mund", "geht", "nicht", "mehr", "zu")
        val tagsAvd = arrayListOf<String>("foo", "bar")
        //val tagsAvd = arrayListOf<String>()

        val filterAdapter = FilterExpanderAdapter(activity, header, filterHashMap, tagsReq, tagsAvd)
        val xmlFilterElement: ExpandableListView?
        xmlFilterElement = view.findViewById(R.id.filter_element) as ExpandableListView
        xmlFilterElement.setAdapter(filterAdapter)


        //-------------------------- DOES NOT WORK PROPERLY YET --------------------------------------------
        val filterListHeader = inflater.inflate(R.layout.filter_list_header, container, false)
        val filterReq = filterListHeader.findViewById<View>(R.id.filter_header_on_required) as LinearLayout
        val filterAvd = filterListHeader.findViewById<View>(R.id.filter_header_on_avoided) as LinearLayout

        // Listview Group expanded listener
        xmlFilterElement.setOnGroupExpandListener(OnGroupExpandListener {
            Toast.makeText(activity, "Es funktioniert! Expandiert!", Toast.LENGTH_SHORT).show()
            //filterReq.visibility = GONE
            //filterAvd.visibility = GONE
        })

        // Listview Group collapsed listener
        xmlFilterElement.setOnGroupCollapseListener(OnGroupCollapseListener {
            Toast.makeText(activity, "Es funktioniert! Kollabiert!", Toast.LENGTH_SHORT).show()
            //filterReq.visibility = VISIBLE
            //filterAvd.visibility = VISIBLE
        })
        //-------------------------- DOES NOT WORK PROPERLY YET --------------------------------------------


        return view
    }



}