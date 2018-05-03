package de.mec_kon.tagventory.first_fragment.adapter

import android.app.Activity
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.LinearLayout
import android.widget.TextView
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.Filter
import de.mec_kon.tagventory.first_fragment.datastructure.Tag

class FilterExpanderAdapter(private val context: Activity,private val listOfHeaderData: ArrayList<String>,private val hashMapOfChildData: HashMap<String,ArrayList<String>>,
                            private val filters:Filter): BaseExpandableListAdapter() {

    lateinit var xmlOnRequired:LinearLayout
    lateinit var xmlOnAvoided:LinearLayout

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val inflater = context.layoutInflater
        val view = inflater.inflate(R.layout.filter_list_header,  parent, false)

        xmlOnRequired = view.findViewById<View>(R.id.filter_header_on_required) as LinearLayout
        xmlOnAvoided = view.findViewById<View>(R.id.filter_header_on_avoided) as LinearLayout
        val xmlNumOfRequired = view.findViewById<View>(R.id.filter_required_count) as TextView
        val xmlNumOfAvoided = view.findViewById<View>(R.id.filter_avoided_count) as TextView

        xmlNumOfRequired.text = filters.counterReq.toString()
        xmlNumOfAvoided.text = filters.counterAvd.toString()

        if (filters.counterReq == 0) xmlOnRequired.removeAllViews()
        if (filters.counterAvd == 0) xmlOnAvoided.removeAllViews()

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

        xmlOnRequired.removeAllViews()
        xmlOnAvoided.removeAllViews()

        val inflater = context.layoutInflater
        val view = inflater.inflate(R.layout.filter_list_item, parent, false)

        val childTitle: String
        val tagList: ArrayList<Tag>


        ////////// respective TextView //////////

        if (childPosition == 0) {
            childTitle = context.getString(R.string.str_filter_required_full)
            tagList = filters.tagsReq
        } else {
            childTitle = context.getString(R.string.str_filter_avoided_full)
            tagList = filters.tagsAvd
        }

        val xmlFilterItemText = view.findViewById<View>(R.id.filter_item_text) as TextView
        xmlFilterItemText.text = childTitle


        ////////// respective item list //////////

        val xmlFilterItemTagList = view.findViewById<View>(R.id.filter_item_taglist) as LinearLayout

        for (i in 0 until tagList.size) {

            // add placeholder textview
            val placeholderTextView = TextView(context)
            placeholderTextView.setPadding(10,0,10,0)
            xmlFilterItemTagList.addView(placeholderTextView)

            // add textview
            val tagView = inflater.inflate(R.layout.inventory_tag_item, null)
            val xmlTagItem = tagView.findViewById(R.id.tag_item) as TextView
            xmlTagItem.text = tagList[i].name

            val roundedTagDesignBG = xmlTagItem.background
            roundedTagDesignBG.setColorFilter(tagList[i].color, PorterDuff.Mode.SRC_ATOP)

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