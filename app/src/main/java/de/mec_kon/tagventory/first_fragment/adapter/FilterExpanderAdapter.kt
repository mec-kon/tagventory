package de.mec_kon.tagventory.first_fragment.adapter

import android.app.Activity
import android.graphics.PorterDuff
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.Tag
import kotlinx.android.synthetic.main.inventory_tag_item.view.*


class FilterExpanderAdapter(private val view:View, private val inflater: LayoutInflater, private val context: Activity) {

    val xmlFilterLinearLayout = view.findViewById<View>(R.id.filter) as LinearLayout
    val xmlFilterContent = inflater.inflate(R.layout.filter_expander, xmlFilterLinearLayout, false)
    val tagListsLayout = xmlFilterContent.findViewById<View>(R.id.filter_tag_lists_layout) as LinearLayout

    private lateinit var recyclerViewRequired: RecyclerView
    private lateinit var viewAdapterRequired: RecyclerView.Adapter<*>
    private lateinit var viewManagerRequired: RecyclerView.LayoutManager


    private lateinit var recyclerViewAvoided: RecyclerView
    private lateinit var viewAdapterAvoided: RecyclerView.Adapter<*>
    private lateinit var viewManagerAvoided: RecyclerView.LayoutManager



    init {
        xmlFilterLinearLayout.addView(xmlFilterContent)
    }

    fun listeners () {
        var expanded = false
        xmlFilterContent.setOnClickListener { view ->

            if(!expanded){
                tagListsLayout.visibility = View.VISIBLE
                expanded = true
            }
            else {
                tagListsLayout.visibility = View.GONE
                expanded = false
            }

        }
    }


    fun createTagLists (tagListRequired: ArrayList<Tag>, tagListAvoided: ArrayList<Tag>){
        viewManagerRequired = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewAdapterRequired = TagListAdapter(tagListRequired, context)
        recyclerViewRequired = view.findViewById<RecyclerView>(R.id.filter_required_tags).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManagerRequired

            // specify an viewAdapterRequired (see also next example)
            adapter = viewAdapterRequired

        }

        viewManagerAvoided = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewAdapterAvoided = TagListAdapter(tagListAvoided, context)
        recyclerViewAvoided = view.findViewById<RecyclerView>(R.id.filter_avoided_tags).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManagerAvoided

            // specify an viewAdapterRequired (see also next example)
            adapter = viewAdapterAvoided
        }
    }

    inner class TagListAdapter(private val tagList: ArrayList<Tag>, private val context: Activity) :
            RecyclerView.Adapter<TagListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagListAdapter.ViewHolder {

            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.inventory_tag_item, parent, false)

            return ViewHolder(itemView)
        }


        override fun onBindViewHolder(holder: TagListAdapter.ViewHolder, position: Int) {
            holder.bindTags(tagList[position])
        }

        override fun getItemCount(): Int {
            return  tagList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bindTags(tag: Tag) {

                itemView.tag_item.text = tagList[position].name
                // set tag color
                val roundedTagDesignBG = itemView.tag_item.background
                // SRC_ATOP makes the colorFilter overlay the xmlTagItem's background (rounded_tag_design.xml)
                roundedTagDesignBG.setColorFilter(tag.color, PorterDuff.Mode.SRC_ATOP)
            }

        }
    }


}