package de.mec_kon.tagventory.first_fragment

import android.app.Fragment
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
import android.view.DragEvent
import android.content.ClipDescription
import android.util.Log

import de.mec_kon.tagventory.saves.Saves
import kotlin.math.log

class FirstFragment : Fragment(), InventoryListAdapter.InventoryListInterface, SearchView.OnQueryTextListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var itemList = arrayListOf<InventoryItem>()
    private var resultingItemList = arrayListOf<InventoryItem>()

    private lateinit var searchBar: SearchView

    private lateinit var saves:Saves

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        searchBar = view.findViewById(R.id.search_field)
        searchBar.setOnQueryTextListener(this)
        val mDragListen = TagDragEventListener()
        searchBar.setOnDragListener(mDragListen)

        saves = Saves(activity)

        itemList = saves.itemList
        for (i in itemList){
            resultingItemList.add(i)
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = InventoryListAdapter(resultingItemList, activity)

        // set this instance of FirstFragment as the implementer of the viewAdapter's InventoryListInterface
        (viewAdapter as InventoryListAdapter).setInventoryListInterfaceImplementer(this)

        recyclerView = view.findViewById<RecyclerView>(R.id.inventory_list).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }


        val filterExpander = FilterExpanderAdapter(view, inflater, activity)
        filterExpander.listeners()
        filterExpander.createTagLists(tagList3, tagList1)


        return view
    }

    fun addItem(){
        val tagList = arrayListOf(Tag("Extra1", rgb(200, 50, 150)),
                Tag("Additionally2", rgb(20, 180, 150)))
        val item = InventoryItem("NewlyAdded", true, 1, tagList)
        itemList.add(item)

        if (searchBar.query == "" || item.name.contains(searchBar.query)) {
            resultingItemList.add(item)
        }

        viewAdapter.notifyDataSetChanged()
        viewManager.scrollToPosition(itemList.size-1)

        saves.itemList = itemList
    }

    private fun updateResultingItemList(){
        resultingItemList.clear()

        if((searchBar.query).toString() == ""){

            for(i in itemList) {
                resultingItemList.add(i)
            }
            viewAdapter.notifyDataSetChanged()

        } else {

            for(i in itemList) {
                if(i.name.contains((searchBar.query).toString())){
                    resultingItemList.add(i)
                }
            }
            viewAdapter.notifyDataSetChanged()

        }
    }



    // OnQueryTextListener implementations

    override fun onQueryTextChange(queryText: String): Boolean {

        updateResultingItemList()

        return true
    }

    override fun onQueryTextSubmit(queryText: String): Boolean {

        return true
    }



    // InventoryListInterface implementations

    override fun onClickInvoked(itemToBeChanged: InventoryItem) {
        /*
        if ((searchBar.query).toString() != "") {
            itemList[itemList.indexOf(itemToBeChanged)].name = (searchBar.query).toString()
            viewAdapter.notifyDataSetChanged()
        }
        */
    }

    override fun onLongClickInvoked(itemToBeChanged: InventoryItem) {
        itemList.removeAt(itemList.indexOf(itemToBeChanged))
        updateResultingItemList()
    }

    override fun onItemAddTag(itemToBeChanged: InventoryItem, tagName: String) {
        val newTag = Tag(tagName, rgb(200, 80, 200))

        itemList[itemList.indexOf(itemToBeChanged)].tagList.add(newTag)
        updateResultingItemList()
    }



    inner class TagDragEventListener : View.OnDragListener {

        override fun onDrag(v: View, event: DragEvent): Boolean {

            val action = event.action

            when (action) {

                // start dragging the view
                DragEvent.ACTION_DRAG_STARTED -> {

                    // determines if this View can accept the dragged data
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                        // returns true to indicate that the View can accept the dragged data.
                        return true
                    }

                    // returns false; during the current drag and drop operation, this View will
                    // not receive events again until ACTION_DRAG_ENDED is sent
                    return false
                }

                // dragging the view into the target's boundaries
                DragEvent.ACTION_DRAG_ENTERED -> {


                    return true
                }

                // ...
                DragEvent.ACTION_DRAG_LOCATION ->
                    // ignore this event
                    return true

                // dragging the view out of the target's boundaries
                DragEvent.ACTION_DRAG_EXITED -> {


                    return true
                }

                // letting go of the dragged view within the boundaries of the targeted view
                DragEvent.ACTION_DROP -> {

                    // get the passed object
                    val receivedTag: Tag = event.localState as Tag

                    searchBar.setQuery(receivedTag.name, false)

                    // returns true; DragEvent.getResult() will return true.
                    return true
                }

                // letting go of the dragged view
                DragEvent.ACTION_DRAG_ENDED -> {

                    // does a getResult() and displays what happened
                    if (event.result) {
                        // Toast.makeText(context, "The drop was handled.", Toast.LENGTH_SHORT).show()

                    } else {
                        // Toast.makeText(context, "The drop didn't work.", Toast.LENGTH_SHORT).show()

                    }

                    // returns true; the value is ignored
                    return true
                }


                else -> {
                    Toast.makeText(context, "Unknown action type received by OnDragListener", Toast.LENGTH_SHORT)
                            .show()
                }

            }

            return false
        }

    }


}