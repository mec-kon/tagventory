package de.mec_kon.tagventory.first_fragment

import android.app.Fragment
import android.graphics.Color.rgb
import android.os.Bundle
import android.support.v7.widget.CardView
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



class FirstFragment : Fragment(), InventoryListAdapter.InventoryListInterface, SearchView.OnQueryTextListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var itemList = arrayListOf<InventoryItem>()
    private var resultingItemList = arrayListOf<InventoryItem>()

    private lateinit var searchBar: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        searchBar = view.findViewById<SearchView>(R.id.search_field)
        searchBar.setOnQueryTextListener(this)
        val mDragListen = TagDragEventListener()
        searchBar.setOnDragListener(mDragListen)



        ////////// create inventory list //////////
        val tag1 = Tag("One", rgb(255, 0, 20))
        val tag2 = Tag("Two", rgb(20, 0, 255))
        val tag3 = Tag("Three", rgb(0, 50, 100))
        val tag4 = Tag("Four", rgb(90, 90, 90))
        val tag5 = Tag("Five", rgb(200, 50, 20))

        val tagList1 = arrayListOf<Tag>(tag1, tag2, tag3, tag4, tag5)
        val tagList2 = arrayListOf<Tag>(tag2, tag4)
        val tagList3 = arrayListOf<Tag>(tag1, tag2, tag5, tag4, tag1, tag2, tag5, tag4, tag1, tag2, tag5, tag4)

        val item1 = InventoryItem("test", true,1, tagList1)
        val item2 = InventoryItem("test2andMore", true,1, tagList2)
        val item3 = InventoryItem("test3More", true,1, tagList3)
        val item4 = InventoryItem("testandMore", true,1, tagList1)
        val item5 = InventoryItem("TestAny", true,1, tagList2)
        val item6 = InventoryItem("AnyTest", true,1, tagList3)
        val item7 = InventoryItem("TEST7", true,1, tagList1)
        val item8 = InventoryItem("teSt8", true,1, tagList2)
        val item9 = InventoryItem("testingAnyMoreand9", true,1, tagList3)

        itemList = arrayListOf<InventoryItem>(item1, item2, item3, item4, item5, item6, item7, item8, item9)
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

/*
        ////////// create filter element //////////
        val header = arrayListOf("")
        val children = arrayListOf("1", "2")
        val filterHashMap = hashMapOf(header[0] to children)

        val filter1 = Filter(tagList1, tagList2)

        val filterAdapter = FilterExpanderAdapter(activity, header, filterHashMap, filter1)
        val xmlFilterElement: ExpandableListView?
        xmlFilterElement = view.findViewById<View>(R.id.filter_element) as ExpandableListView
        xmlFilterElement.setAdapter(filterAdapter)

*/


        return view
    }

    fun addItem(){
        val tagList = arrayListOf<Tag>(Tag("Extra1", rgb(200, 50, 150)),
                Tag("Additionally2", rgb(20, 180, 150)))
        val item = InventoryItem("NewlyAdded", true, 1, tagList)
        itemList.add(item)

        if (searchBar.query == "" || item.name.contains(searchBar.query)) {
            resultingItemList.add(item)
        }

        viewAdapter.notifyDataSetChanged()
        viewManager.scrollToPosition(itemList.size-1)
    }



    // OnQueryTextListener implementations

    override fun onQueryTextChange(queryText: String): Boolean {

        resultingItemList.clear()

        if(queryText == ""){

            for(i in itemList) {
                resultingItemList.add(i)
            }
            viewAdapter.notifyDataSetChanged()

        } else {

            for(i in itemList) {
                if(i.name.contains(queryText)){
                    resultingItemList.add(i)
                }
            }
            viewAdapter.notifyDataSetChanged()

        }

        return true
    }

    override fun onQueryTextSubmit(queryText: String): Boolean {

        return true
    }


    // InventoryListInterface implementations

    override fun onClickInvoked(position: Int) {
        if ((searchBar.query).toString() != "") {
            itemList[position].name = (searchBar.query).toString()
            viewAdapter.notifyItemChanged(position)
        }
    }

    override fun onLongClickInvoked(position: Int) {
        itemList.removeAt(position)
        viewAdapter.notifyDataSetChanged()
    }

    override fun onItemAddTag(position: Int, tagName: String) {
        val newTag = Tag(tagName, rgb(200, 80, 200))
        itemList[position].tagList.add(newTag)
        viewAdapter.notifyItemChanged(position)
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