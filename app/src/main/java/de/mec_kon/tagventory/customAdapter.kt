package de.mec_kon.tagventory

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView

class customAdapter(private val context: Activity, private val testItems:ArrayList<String>, private  val testTags:ArrayList<String>) : ArrayAdapter<String>(context, R.layout.single_inventory_list_item, testItems) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.single_inventory_list_item, null, true)

        val firstElement = rowView.findViewById<View>(R.id.list_item_1) as TextView
        val secondElement = rowView.findViewById<View>(R.id.list_item_2) as LinearLayout


        firstElement.text = testItems[position]

        for (i in 0 until  testTags.size){
            val testView: TextView = TextView(context)
            testView.setText(testTags[i])
            secondElement.addView(testView)
        }

        return rowView
    }
}
