package de.mec_kon.tagventory.first_fragment

import android.app.Fragment
import android.support.v7.app.AlertDialog
import android.graphics.Color.rgb
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import de.mec_kon.tagventory.R
import de.mec_kon.tagventory.first_fragment.adapter.FilterExpanderAdapter
import de.mec_kon.tagventory.first_fragment.adapter.InventoryListAdapter
import de.mec_kon.tagventory.first_fragment.datastructure.InventoryItem
import de.mec_kon.tagventory.first_fragment.datastructure.Tag
import android.widget.Toast
import de.mec_kon.tagventory.saves.Saves
import kotlinx.android.synthetic.main.edit_item_dialog.view.*
import android.widget.Button
import de.mec_kon.tagventory.first_fragment.adapter.TagListAdapter

/**
 * The main fragment on startup.
 *
 * The first fragment contains the iconic search bar, filter and inventory item list.
 * However, rather than the itemList itself, only the resultingItemList,
 * which contains those elements from the itemList that match the current search bar query,
 * is displayed to the user.
 *
 * @see InventoryItem for the itemList's data type
 * @see InventoryListAdapter for the resultingItemList's adapter
 * @see FilterExpanderAdapter for the tag filter's adapter (which doesn't do much just yet)
 */
class FirstFragment : Fragment(), InventoryListAdapter.InventoryListInterface, SearchView.OnQueryTextListener {

    // inventory item list
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemViewAdapter: RecyclerView.Adapter<*>
    private lateinit var itemViewManager: RecyclerView.LayoutManager

    // tag list within the editItemDialog
    private lateinit var dialogTagRecyclerView: RecyclerView
    private lateinit var dialogTagViewAdapter: RecyclerView.Adapter<*>
    private lateinit var dialogTagViewManager: RecyclerView.LayoutManager

    // contains every item
    private var itemList = arrayListOf<InventoryItem>()
    // only contains those items from the itemList, that match the search bar's query
    private var resultingItemList = arrayListOf<InventoryItem>()

    private lateinit var searchBar: SearchView

    private lateinit var saves: Saves

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        // search bar
        searchBar = view.findViewById(R.id.search_field)
        searchBar.setOnQueryTextListener(this)

        // load saved data into itemList
        saves = Saves(activity)
        itemList = saves.itemList
        for (i in itemList){
            resultingItemList.add(i)
        }


        // make the inventory item list display the resultingItemList
        itemViewManager = LinearLayoutManager(activity)
        itemViewAdapter = InventoryListAdapter(resultingItemList, activity)

        // set this instance of FirstFragment as the implementer of the itemViewAdapter's InventoryListInterface
        (itemViewAdapter as InventoryListAdapter).setInventoryListInterfaceImplementer(this)

        itemRecyclerView = view.findViewById<RecyclerView>(R.id.inventory_list).apply {
            // setting to improve performance when layout size of the inventory item list stays fixed
            setHasFixedSize(true)

            layoutManager = itemViewManager
            adapter = itemViewAdapter
        }


        // THERE IS NO FUNCTIONALITY WITHIN THE FILTER YET
        val filterExpander = FilterExpanderAdapter(view, inflater, activity)
        filterExpander.listeners()

        val exampleTag1 = Tag("ExOne", rgb(255, 0, 20))
        val exampleTag2 = Tag("ExTwo", rgb(20, 0, 255))
        val exampleTag3 = Tag("ExThree", rgb(0, 50, 100))
        val exampleTag4 = Tag("ExFour", rgb(90, 90, 90))

        val exampleTagList1 = arrayListOf(exampleTag1, exampleTag2, exampleTag3)
        val exampleTagList2 = arrayListOf(exampleTag4, exampleTag1, exampleTag3)

        filterExpander.createTagLists(exampleTagList1, exampleTagList2)


        return view
    }

    /**
     * Adds new items into the inventoryList.
     *
     * After the item has been added to the inventoryList,
     * the resultingItemList (which contains all the results for the search query)
     * is updated accordingly.
     *
     * @param itemName the name of the new item
     * @param itemCounter the counter for the new item
     * @param tagList the item's tag list
     */
    private fun addItem(itemName: String, itemCounter: Int, tagList: ArrayList<Tag>){
        // create new a item with provided attributes and add it to the itemList
        val item = InventoryItem(itemName, itemCounter, tagList)
        itemList.add(item)

        // update the resultingItemList accordingly
        if (searchBar.query == "" || item.name.contains(searchBar.query)) {
            resultingItemList.add(item)
        }
        itemViewAdapter.notifyDataSetChanged()

        itemViewManager.scrollToPosition(itemList.size-1)

        // save changes to itemList
        saves.itemList = itemList
    }

    /**
     * Rebuilds the resultingItemList.
     *
     * Finds all items which names match the search query.
     * If there's no query, every item is added to the list.
     */
    private fun updateResultingItemList(){
        resultingItemList.clear()

        if((searchBar.query).toString() == ""){
            // add every item from the itemList, when there is no query
            for(i in itemList) {
                resultingItemList.add(i)
            }
            itemViewAdapter.notifyDataSetChanged()

        } else {
            // add results from the itemList, when the query matches
            for(i in itemList) {
                if(i.name.contains((searchBar.query).toString(), ignoreCase = true)){
                    resultingItemList.add(i)
                }
            }
            itemViewAdapter.notifyDataSetChanged()

        }
    }

    /**
     * Launches the dialog for item editing
     *
     * This dialog allows to edit the name, counter and tag list of a new or already existing item,
     * as well as deleting the whole item from the inventoryList.
     * Any changes to items are discarded if the user does not apply the confirm button.
     *
     * @param itemToEdit the InventoryItem that is being edited
     * @param isNew indicates, whether the itemToEdit does not yet exist within the inventoryList
     * @see TagListAdapter for the tag list
     */
    fun showEditItemDialog(itemToEdit: InventoryItem, isNew: Boolean) {

        val viewGroup = view.findViewById<ViewGroup>(android.R.id.content)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.edit_item_dialog, viewGroup, false)
        val builder = AlertDialog.Builder(context)

        // copy the itemsToEdit's tag list to prevent it from being affected by instant changes
        val newTagList = arrayListOf<Tag>()
        for (i in itemToEdit.tagList) {
            newTagList.add(i)
        }

        // make the dialog's tag list display the itemsToEdit's tags
        dialogTagViewManager = LinearLayoutManager(activity)
        dialogTagViewAdapter = TagListAdapter(newTagList)
        dialogTagRecyclerView = dialogView.findViewById<RecyclerView>(R.id.edit_item_tag_list).apply {
            // setting to improve performance when layout size of the inventory item list stays fixed
            setHasFixedSize(true)

            layoutManager = dialogTagViewManager
            adapter = dialogTagViewAdapter
        }

        // make the dialog's item name display the itemsToEdit's name
        dialogView.edit_item_name.setText(itemToEdit.name)
        // make the dialog's item counter display the itemsToEdit's counter
        dialogView.edit_item_counter.setText(itemToEdit.counter.toString())

        val buttonConfirm = dialogView.findViewById<Button>(R.id.edit_item_confirm)
        val buttonDelete = dialogView.findViewById<Button>(R.id.edit_item_delete)

        builder.setView(dialogView)
        val editItemDialog = builder.create()



        //////////////////// onClick / onAction listeners ////////////////////

        // add a new tag to the dialog's tag list
        dialogView.edit_item_add_tag_input.setOnEditorActionListener { _, _, _ ->
            val newTagName = dialogView.edit_item_add_tag_input.text.toString()

            if (newTagName != "") {
                newTagList.add(0, Tag(newTagName, rgb(200, 80, 200)))
                dialogTagViewAdapter.notifyDataSetChanged()
                dialogView.edit_item_add_tag_input.setText("")
            }
            true
        }

        // invoke a buttonConfirm click when pressing the enter key on the dialog's name field
        dialogView.edit_item_name.setOnEditorActionListener { _, _, _ ->
            buttonConfirm.callOnClick()
            true
        }

        // invoke a buttonConfirm click when pressing the enter key on the dialog's counter field
        dialogView.edit_item_counter.setOnEditorActionListener { _, _, _ ->
            buttonConfirm.callOnClick()
            true
        }

        // apply changes made to the itemToEdit by updating its name, counter and tag list
        buttonConfirm.setOnClickListener {
            val newItemName = dialogView.edit_item_name.text.toString()
            val itemCounterUsage = dialogView.edit_item_counter.text.toString()

            var newItemCount = 0
            if (itemCounterUsage != "") {
                newItemCount = Integer.valueOf(itemCounterUsage)
            }

            if (newItemName != "" && isNew) {
                // add a new item to the itemList
                addItem(newItemName, newItemCount, newTagList)
                editItemDialog.dismiss()
            } else if (newItemName != "" && !isNew) {
                // update an already existing item in the itemList
                itemList[itemList.indexOf(itemToEdit)].name = newItemName
                itemList[itemList.indexOf(itemToEdit)].counter = newItemCount
                itemList[itemList.indexOf(itemToEdit)].tagList = newTagList

                // update the resultingItemList and save changes to the itemList
                updateResultingItemList()
                saves.itemList = itemList

                editItemDialog.dismiss()
            } else {
                Toast.makeText(context, "Please enter an item name", Toast.LENGTH_SHORT).show()
            }
        }

        // delete the itemToEdit and dismiss the dialog
        buttonDelete.setOnClickListener {
            if(!isNew) {
                itemList.removeAt(itemList.indexOf(itemToEdit))
                updateResultingItemList()
            }
            editItemDialog.dismiss()
        }



        editItemDialog.show()
    }



    //////////////////// OnQueryTextListener implementations ////////////////////
    /**
     * Implements onQueryTextChange.
     *
     * This function is called whenever the search query is being edited.
     *
     * @param queryText the query within the search bar
     */
    override fun onQueryTextChange(queryText: String): Boolean {
        updateResultingItemList()

        return true
    }

    /**
     * Implements onQueryTextSubmit.
     *
     * This function is called whenever the search query is being submitted.
     *
     * @param queryText the query within the search bar
     */
    override fun onQueryTextSubmit(queryText: String): Boolean {
        // placeholder
        return true
    }



    //////////////////// InventoryListInterface implementations ////////////////////
    /**
     * Implements onClickInvoked.
     *
     * This function is called when an item from the resultingItemList has been clicked on.
     *
     * @param itemToBeChanged the item within the resultingItemList, which invoked this function
     * @see InventoryListAdapter.InventoryListInterface.onClickInvoked for details
     */
    override fun onClickInvoked(itemToBeChanged: InventoryItem) {
        /*
        if ((searchBar.query).toString() != "") {
            itemList[itemList.indexOf(itemToBeChanged)].name = (searchBar.query).toString()
            itemViewAdapter.notifyDataSetChanged()
        }
        */
    }

    /**
     * Implements onLongClickInvoked.
     *
     * This function is called when an item from the resultingItemList has been clicked on and held.
     *
     * @param itemToBeChanged the item within the resultingItemList, which invoked this function
     * @see InventoryListAdapter.InventoryListInterface.onLongClickInvoked for details
     */
    override fun onLongClickInvoked(itemToBeChanged: InventoryItem) {
        showEditItemDialog(itemList[itemList.indexOf(itemToBeChanged)], false)
    }

    /**
     * Implements onItemAddTag.
     *
     * This function is called when an item's addTagInput triggers its OnEditorActionListener.
     *
     * @param itemToBeChanged the item within the resultingItemList, whose addTagInput invoked this function
     * @see InventoryListAdapter.InventoryListInterface.onItemAddTag for details
     */
    override fun onItemAddTag(itemToBeChanged: InventoryItem, tagName: String) {
        // create new a tag with provided attributes and add it to the passed itemToBeChanged
        val newTag = Tag(tagName, rgb(200, 80, 200))
        itemList[itemList.indexOf(itemToBeChanged)].tagList.add(0, newTag)

        updateResultingItemList()

        // save changes to itemList
        saves.itemList = itemList
    }



}