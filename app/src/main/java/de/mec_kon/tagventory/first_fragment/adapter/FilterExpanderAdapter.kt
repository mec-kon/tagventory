package de.mec_kon.tagventory.first_fragment.adapter

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.Tag

/**
 * Adapter for the tag filter.
 *
 * THERE IS NO FUNCTIONALITY WITHIN THE FILTER YET.
 *
 * @param inflater
 * @property view
 * @property context
 */
class FilterExpanderAdapter(private val view:View, inflater: LayoutInflater, private val context: Activity) {
    private val xmlFilterLinearLayout = view.findViewById<View>(R.id.filter) as LinearLayout
    private val xmlFilterContent = inflater.inflate(R.layout.filter_expander, xmlFilterLinearLayout, false)
    private val tagListsLayout = xmlFilterContent.findViewById<View>(R.id.filter_tag_lists_layout) as LinearLayout

    private lateinit var requiredTagRecyclerView: RecyclerView
    private lateinit var requiredTagViewAdapter: RecyclerView.Adapter<*>
    private lateinit var requiredTagViewManager: RecyclerView.LayoutManager

    private lateinit var avoidedTagRecyclerView: RecyclerView
    private lateinit var avoidedTagViewAdapter: RecyclerView.Adapter<*>
    private lateinit var avoidedTagViewManager: RecyclerView.LayoutManager

    init {
        xmlFilterLinearLayout.addView(xmlFilterContent)
    }

    /**
     * Sets an OnClickListener for the filter.
     *
     * This OnClickListener toggles the visibility of the filter
     * by expanding or collapsing it accordingly.
     */
    fun listeners () {
        var expanded = false

        xmlFilterContent.setOnClickListener {
            if(!expanded){
                tagListsLayout.visibility = View.VISIBLE
                expanded = true
            } else {
                tagListsLayout.visibility = View.GONE
                expanded = false
            }
        }

    }

    /**
     * Make the filter display the corresponding tag lists.
     *
     * This function sets up the TagListAdapters for the two tag lists within the filter.
     *
     * @param tagListRequired the list that contains the required tags
     * @param tagListAvoided the list that contains the avoided tags
     * @see TagListAdapter for the tag lists
     */
    fun createTagLists (tagListRequired: ArrayList<Tag>, tagListAvoided: ArrayList<Tag>){
        // required tags list
        requiredTagViewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        requiredTagViewAdapter = TagListAdapter(tagListRequired)
        requiredTagRecyclerView = view.findViewById<RecyclerView>(R.id.filter_required_tags).apply {
            // setting to improve performance when layout size of the inventory item list stays fixed
            setHasFixedSize(true)

            layoutManager = requiredTagViewManager
            adapter = requiredTagViewAdapter
        }
        // avoided tags list
        avoidedTagViewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        avoidedTagViewAdapter = TagListAdapter(tagListAvoided)
        avoidedTagRecyclerView = view.findViewById<RecyclerView>(R.id.filter_avoided_tags).apply {
            // setting to improve performance when layout size of the inventory item list stays fixed
            setHasFixedSize(true)

            layoutManager = avoidedTagViewManager
            adapter = avoidedTagViewAdapter
        }
    }



}