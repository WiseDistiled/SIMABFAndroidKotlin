package com.tiramakan.simabf.ui.view.Lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.core.models.realm.Depot
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterDepot

import io.realm.RealmResults
import io.realm.Sort

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [ListDepotUI]
 * interface.
 */
class ListDepotUI : BaseFragment() {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BootstrapApplication.component().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.depot_item_list, container, false)

        // Set the adapter
        val recyclerView = view.findViewById<View>(R.id.my_recycler_view) as RecyclerView

        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context)

        val depots = serviceProvider.service.depots
        depots.sort("nom", Sort.ASCENDING)
        val recyclerViewAdapterDepot = depots.let { RecyclerViewAdapterDepot(context,it) }
        recyclerView.adapter = recyclerViewAdapterDepot


        return view


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    override fun doBack(): Boolean {
        return true
    }

}
