package com.tiramakan.simabf.ui.view.Lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterMarche

import io.realm.Sort

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [ListMarcheUI]
 * interface.
 */
class ListMarcheUI : BaseFragment() {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    internal lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BootstrapApplication.component().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.marche_item_list_ui, container, false)

        // Set the adapter
        recyclerView = view.findViewById<View>(R.id.my_recycler_view) as RecyclerView

        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context)

        val marches = serviceProvider.service.marches
        marches.sort("nom", Sort.ASCENDING)
        val recyclerViewAdapterMarche = RecyclerViewAdapterMarche(context,marches)
        recyclerView.adapter = recyclerViewAdapterMarche


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
