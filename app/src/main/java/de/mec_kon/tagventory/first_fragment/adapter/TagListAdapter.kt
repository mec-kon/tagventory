package de.mec_kon.tagventory.first_fragment.adapter

import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.datastructure.Tag
import kotlinx.android.synthetic.main.inventory_tag_item.view.*


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



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindTag(listedTag: Tag) {
            itemView.tag_item.text = listedTag.name

            // set tag color
            val roundedTagDesignBG = itemView.tag_item.background
            // SRC_ATOP makes the colorFilter overlay the xmlTagItem's background (rounded_tag_design.xml)
            roundedTagDesignBG.setColorFilter(listedTag.color, PorterDuff.Mode.SRC_ATOP)
        }

    }


}