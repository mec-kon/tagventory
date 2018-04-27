package de.mec_kon.tagventory

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class FilterExpanderAdapter(val context: Activity, val listOfHeaderData: ArrayList<String>, val hashMapOfChildData: HashMap<String,ArrayList<String>>): BaseExpandableListAdapter() {

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val headerTitle = listOfHeaderData[groupPosition]
        val inflater = context.layoutInflater
        val itemView = inflater.inflate(R.layout.filter_list_header,  parent, false)

        val listHeaderText = itemView.findViewById<View>(R.id.filter_header_text_view) as TextView
        listHeaderText.text =  headerTitle

        return itemView
    }

    // number of list elements
    override fun getGroupCount(): Int {
        return listOfHeaderData.size
    }

    // get the current group element
    override fun getGroup(groupPosition: Int): Any {
        return listOfHeaderData[groupPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }


    override fun hasStableIds(): Boolean {
        return true
    }



    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.layoutInflater
        val itemView = inflater.inflate(R.layout.filter_list_item, parent, false)

        val childTitle = getChild(groupPosition, childPosition) as String


        val listHeaderText = itemView.findViewById<View>(R.id.filter_list_item_text) as TextView
        listHeaderText.text =  childTitle


        return itemView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }


    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return hashMapOfChildData[listOfHeaderData[groupPosition]]!![childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }





    /*
    override fun getGroup(p0: Int): Any {
        return listOfHeaderData[p0]
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasStableIds(): Boolean {
        return false
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChildrenCount(p0: Int): Int {
        return 1 //hashMapOfChildData[listOfHeaderData[p0]]!!.size
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return 0
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val headerTitle = "chil"// getChild(p0, p1) as String
        val inflater = context.layoutInflater
        val itemView = inflater.inflate(R.layout.filter_list_item, p4, true)

        val listHeaderText = itemView.findViewById<View>(R.id.filter_list_item_text) as TextView
        listHeaderText.text = headerTitle

        return itemView

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroupCount(): Int {
        return listOfHeaderData.size
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    */

}