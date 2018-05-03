package de.mec_kon.tagventory.first_fragment.datastructure

class Filter(var tagsReq:ArrayList<Tag>, var tagsAvd:ArrayList<Tag>) {
    var counterReq = tagsReq.size
    var counterAvd = tagsAvd.size

    fun addTag(tag: Tag, filterList:ArrayList<Tag>){
        filterList.add(tag)
    }

    fun removeTag(tag: Tag, filterList:ArrayList<Tag>){
        filterList.remove(tag)
    }
}