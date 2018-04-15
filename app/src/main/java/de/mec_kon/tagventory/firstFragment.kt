package de.mec_kon.tagventory

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ArrayAdapter



class firstFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_first, container, false)

        var XMLentryList: ListView?
        XMLentryList = view.findViewById<View>(R.id.inventory_list) as ListView

        val list1 = arrayListOf<String>("a", "b", "c")


        val adapter = customAdapter(activity, list1)
        XMLentryList.adapter = adapter




        return view
    }
}