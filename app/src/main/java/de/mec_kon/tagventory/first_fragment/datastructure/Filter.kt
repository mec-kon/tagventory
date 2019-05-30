package de.mec_kon.tagventory.first_fragment.datastructure

/**
 * The filter that in- or excludes results from the itemList based on their tags.
 *
 * THERE IS NO FUNCTIONALITY WITHIN THIS CLASS YET.
 */
class Filter(var tagsReq:ArrayList<Tag>, var tagsAvd:ArrayList<Tag>) {
    var counterReq = tagsReq.size
    var counterAvd = tagsAvd.size
}