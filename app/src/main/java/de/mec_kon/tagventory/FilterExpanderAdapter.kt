package de.mec_kon.tagventory

import android.app.Activity
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.LinearLayout
import android.widget.TextView

class FilterExpanderAdapter(private val context: Activity,private val listOfHeaderData: ArrayList<String>,private val hashMapOfChildData: HashMap<String,ArrayList<String>>,
                            private val taglistRequired: ArrayList<String>,private val taglistAvoided: ArrayList<String>): BaseExpandableListAdapter() {

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val inflater = context.layoutInflater
        val view = inflater.inflate(R.layout.filter_list_header,  parent, false)

        val xmlOnRequired = view.findViewById<View>(R.id.filter_header_on_required) as LinearLayout
        val xmlOnAvoided = view.findViewById<View>(R.id.filter_header_on_avoided) as LinearLayout
        val xmlNumOfRequired = view.findViewById<View>(R.id.filter_required_count) as TextView
        val xmlNumOfAvoided = view.findViewById<View>(R.id.filter_avoided_count) as TextView

        xmlNumOfRequired.text = taglistRequired.size.toString()
        xmlNumOfAvoided.text = taglistAvoided.size.toString()

        if (taglistRequired.size == 0) xmlOnRequired.visibility = GONE
        if (taglistAvoided.size == 0) xmlOnAvoided.visibility = GONE

        return view
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
        val view = inflater.inflate(R.layout.filter_list_item, parent, false)

        val childTitle: String
        val taglist: ArrayList<String>


        ////////// respective TextView //////////

        if (childPosition == 0) {
            childTitle = context.getString(R.string.str_filter_required_full)
            taglist = taglistRequired
        } else {
            childTitle = context.getString(R.string.str_filter_avoided_full)
            taglist = taglistAvoided
        }

        val xmlFilterItemText = view.findViewById<View>(R.id.filter_item_text) as TextView
        xmlFilterItemText.text = childTitle



        ////////// respective item list //////////

        val xmlFilterItemTagList = view.findViewById<View>(R.id.filter_item_taglist) as LinearLayout

        for (i in 0 until taglist.size) {

            // add placeholder textview
            val placeholderTextView = TextView(context)
            placeholderTextView.setPadding(10,0,10,0)
            xmlFilterItemTagList.addView(placeholderTextView)

            // add textview
            val tagView = inflater.inflate(R.layout.inventory_tag_item, null)
            val xmlTagItem = tagView.findViewById(R.id.tag_item) as TextView
            xmlTagItem.text = taglist[i]
            xmlFilterItemTagList.addView(xmlTagItem)
        }


        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        //elvis operator
        return hashMapOfChildData[listOfHeaderData[groupPosition]]?.size ?: 0
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return hashMapOfChildData[listOfHeaderData[groupPosition]]!![childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

}