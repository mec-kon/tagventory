package de.mec_kon.tagventory.first_fragment.datastructure

class Filter(private var tagsAll:ArrayList<Tag>, private var tagsReq:ArrayList<Tag>, private var tagsAvd:ArrayList<Tag>) {
    private var counterReq = tagsReq.size
    private var counterAvd = tagsAvd.size

    fun addTag(tag: Tag, filterList:ArrayList<Tag>){
        filterList.add(tag)
    }

    fun removeTag(tag: Tag, filterList:ArrayList<Tag>){
        filterList.remove(tag)
    }
}