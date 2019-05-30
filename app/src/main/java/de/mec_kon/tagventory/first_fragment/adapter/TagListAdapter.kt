package de.mec_kon.tagventory.first_fragment.adapter

import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.Tag
import kotlinx.android.synthetic.main.inventory_tag_item.view.*

/**
 * Adapter for the tag list.
 *
 * This class is used to bind a list of tags.
 *
 * @property tagList the list of tags that is bound to the corresponding RecyclerView
 */
class TagListAdapter(private val tagList: ArrayList<Tag>) :
        RecyclerView.Adapter<TagListAdapter.ViewHolder>() {

    override fun getItemCount() = tagList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listedTag = tagList[position]
        holder.bindTag(listedTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.inventory_tag_item, parent, false)

        return ViewHolder(view)
    }



    /**
     * ViewHolder for the TagListAdapter.
     *
     * This class is used to bind a tag to its view.
     *
     * @property itemView the view that the tag is bound to
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindTag(listedTag: Tag) {
            // set the tag_item's text to the corresponding name
            itemView.tag_item.text = listedTag.name

            // set tag_item's color to the corresponding color
            val roundedTagDesignBG = itemView.tag_item.background
            // SRC_ATOP makes the colorFilter overlay the tag_item's background (rounded_tag_design.xml)
            roundedTagDesignBG.setColorFilter(listedTag.color, PorterDuff.Mode.SRC_ATOP)
        }

    }



}