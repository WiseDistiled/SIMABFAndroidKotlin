package com.tiramakan.simabf.ui.view.widgets

/**
 * Created by tiramakan on 17/01/2016.
 */
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.MesureUIModel
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.ui.view.ResumePriceRequest
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterMesureRadio

import java.util.ArrayList

/**
 * Created by tiramakan on 17/01/2016.
 */
class ChoiceOfReqMesure : BaseFragment(), RecyclerViewAdapterMesureRadio.VHItem.ClickListener {
    internal lateinit var mRecyclerView: RecyclerView
    internal lateinit var adapter: RecyclerViewAdapterMesureRadio
    var marcheChoisi : String=""
    var produitChoisi : String=""
    internal var mybundle=Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        if (arguments != null) {
            marcheChoisi = arguments?.getString(Constants.Extra.NOM_MARCHE_TAG).toString()
            produitChoisi = arguments?.getString(Constants.Extra.NOM_PRODUIT_TAG).toString()
            val realm = realmServiceProvider.realm
            val produit = realm.where(Produit::class.java)?.equalTo("nom", produitChoisi)?.findFirst()
           // setSelectedItem(produit?.mesure?.nom?:"")
        }

        setHasOptionsMenu(true)
    }

    override fun doBack(): Boolean {
        return true
    }

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.measure_item_list_radio, container, false)
        val searchText = view.findViewById<View>(R.id.searchText) as EditText
        searchText.setOnKeyListener { _, _, _ ->
            val mesureUIModel = MesureUIModel.getMesureStartingWith(serviceProvider.service, searchText.text.toString())
            setSelectedItem(mesureUIModel.name)
            mRecyclerView.layoutManager?.scrollToPosition(adapter.getPositionByName(mesureUIModel.name.toString()))
            false
        }
        mRecyclerView = view.findViewById<View>(R.id.my_recycler_view) as RecyclerView
        mRecyclerView.setHasFixedSize(false)
        val gridLayoutManager: GridLayoutManager

        if (UIUtils.isTablet(requireContext())) {
            if (UIUtils.isPaysage(requireContext())) {
                gridLayoutManager = GridLayoutManager(context, 7)
            } else {
                gridLayoutManager = GridLayoutManager(context, 5)
            }
        } else {
            if (UIUtils.isPaysage(requireContext())) {
                gridLayoutManager = GridLayoutManager(context, 4)
            } else {
                gridLayoutManager = GridLayoutManager(context, 3)
            }
        }
        mRecyclerView.layoutManager = gridLayoutManager
        adapter = RecyclerViewAdapterMesureRadio(requireContext(),MesureUIModel.getList(serviceProvider.service), this)




        mRecyclerView.adapter = adapter
        setSubTitle(view)

        return view

    }

    fun setSelectedItem(itemName: String?) {
        if (itemName != null) {
            val mesurePosition = adapter.getPositionByName(itemName)
            if (mesurePosition > -1)
                adapter.setSelection(mesurePosition)
        }
    }

    protected fun setSubTitle(view: View) {
        var subtitleString = ""
        val subTitleTextView = view.findViewById<View>(R.id.subTitle) as TextView

        subtitleString = subtitleString + System.getProperty("line.separator")!!
        subtitleString = subtitleString +  "Marché choisi : " + marcheChoisi
        subtitleString = subtitleString + System.getProperty("line.separator")!!
        subtitleString = subtitleString +  "Produit choisi : " + produitChoisi
        subtitleString = subtitleString + System.getProperty("line.separator")!!
        subTitleTextView.text = subtitleString

    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onItemClicked(position: Int) {

        toggleSelection(position)
    }

    override fun onItemLongClicked(position: Int): Boolean {
        toggleSelection(position)
        validate()
        return true
    }

    private fun validate() {
        if (adapter.selectedItem != null) {
            mybundle.putString(Constants.Extra.NOM_MESURE_TAG, adapter.selectedItem?.name.toString())
            mybundle.putString(Constants.Extra.NOM_MARCHE_TAG, marcheChoisi)
            mybundle.putString(Constants.Extra.NOM_PRODUIT_TAG, produitChoisi)

            val resumeRequest = ResumePriceRequest()
            resumeRequest.arguments = mybundle
            addFragment(resumeRequest)
        } else
            bus.post("aucune mesure sélectionnée ")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId
        when (id) {
            R.id.action_next -> validate()
        }
        return true
    }

    private fun toggleSelection(position: Int) {
        adapter.clearSelection()
        adapter.toggleSelection(position)

    }

}
