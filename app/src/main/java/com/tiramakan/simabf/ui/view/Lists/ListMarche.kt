package com.tiramakan.simabf.ui.view.Lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.BootstrapServiceProvider
import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.models.realm.Marche
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterMarche

import javax.inject.Inject

import io.realm.RealmResults
import io.realm.Sort

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [ListMarche]
 * interface.
 */
class ListMarche : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    // TODO: Customize parameters

    internal lateinit var serviceProvider: BootstrapServiceProvider
        @Inject set
    internal lateinit var bus: Bus
        @Inject set
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BootstrapApplication.component().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.marche_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)

            val   marches = serviceProvider.service.marches
            marches.sort("nom", Sort.ASCENDING)
            view.adapter = marches.let { RecyclerViewAdapterMarche(context,it) }


        }

        return view
    }


    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        /**
         * Mandatory empty constructor for the fragment manager to instantiate the
         * fragment (e.g. upon screen orientation changes).
         */

        // TODO: Customize parameter initialization
        fun newInstance(): ListMarche {
            val fragment = ListMarche()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
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

}
