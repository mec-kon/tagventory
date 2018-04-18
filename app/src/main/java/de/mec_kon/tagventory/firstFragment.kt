package de.mec_kon.tagventory

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView



class firstFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_first, container, false)

        var XMLentryList: ListView?
        XMLentryList = view.findViewById<View>(R.id.inventory_list) as ListView


        val item = InventoryItem("test", arrayListOf("tag1", "tag2", "tag3", "tag4", "tag5"))
        val item2 = InventoryItem("test2", arrayListOf("one", "two", "three"))
        val item3 = InventoryItem("test2", arrayListOf("eins", "zwei", "drei", "vier", "fuenf", "sechs"))
        val list = arrayListOf<InventoryItem>(item, item2, item3)


        val adapter = InventoryListAdapter(activity, list)
        XMLentryList.adapter = adapter.innerAdapter




        return view
    }
}