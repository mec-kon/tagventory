package de.mec_kon.tagventory.first_fragment.adapter

import android.app.Activity
import android.graphics.PorterDuff
import android.support.v7.recyclerview.R.attr.layoutManager
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.InventoryItem
import de.mec_kon.tagventory.first_fragment.datastructure.Tag
import kotlinx.android.synthetic.main.inventory_tag_item.view.*


class FilterExpanderAdapter(private val view:View, private val inflater: LayoutInflater, private val context: Activity) {

    val xmlFilterCardView = view.findViewById<View>(R.id.filter) as CardView
    val xmlFilterContent = inflater.inflate(R.layout.filter_expander, xmlFilterCardView, false)
    val avoidedTextView = xmlFilterContent.findViewById<View>(R.id.filter_avoided) as TextView
    val requiredTextView = xmlFilterContent.findViewById<View>(R.id.filter_required) as TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    init {
        xmlFilterCardView.addView(xmlFilterContent)
    }

    fun listeners () {
        var expanded = false
        xmlFilterContent.setOnClickListener { view ->

            if(!expanded){
                avoidedTextView.visibility = View.VISIBLE
                requiredTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE

                expanded = true
            }
            else {
                avoidedTextView.visibility = View.GONE
                requiredTextView.visibility = View.GONE
                recyclerView.visibility = View.GONE
                expanded = false
            }

        }
    }


    fun createTagList (tagList: ArrayList<Tag>){
        viewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewAdapter = TagListAdapter(tagList, context)


        recyclerView = view.findViewById<RecyclerView>(R.id.filter_required_tags).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

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