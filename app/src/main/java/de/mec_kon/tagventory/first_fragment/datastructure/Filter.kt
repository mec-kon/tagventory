package de.mec_kon.tagventory.first_fragment.datastructure

class Filter(var tagsReq:ArrayList<Tag>, var tagsAvd:ArrayList<Tag>) {
    var counterReq = tagsReq.size
    var counterAvd = tagsAvd.size
}