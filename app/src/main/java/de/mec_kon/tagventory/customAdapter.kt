package de.mec_kon.tagventory

import android.app.Activity
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class customAdapter(private val context: Activity, private val list1:ArrayList<String>) : ArrayAdapter<String>(context, R.layout.single_inventory_list_item, list1) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.single_inventory_list_item, null, true)

        val firstElement = rowView.findViewById<View>(R.id.list_item_1) as TextView
        val secondElement = rowView.findViewById<View>(R.id.list_item_1) as TextView


        firstElement.text = list1[position]
        secondElement.text = list1[position]

        return rowView
    }
}
