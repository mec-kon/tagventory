package de.mec_kon.tagventory.first_fragment.datastructure

/**
 * The name giving inventory item
 *
 * These are the elements used in the itemList, which is the "heart" of Tagventory.
 *
 * @property name the name of the item
 * @property counter the optional number representing the item count
 * @property tagList a list of tags describing the item
 * @see Tag for tagList's data type
 */
class InventoryItem (var name:String, var counter: Int, var tagList:ArrayList<Tag>)