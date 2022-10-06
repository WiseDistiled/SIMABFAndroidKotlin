package com.tiramakan.simabf.ui.view

/**
 * Created by tiramakan on 17/01/2016.
 */
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.StockRequestModel
import com.tiramakan.simabf.core.modelView.DepotUIModel
import com.tiramakan.simabf.core.myEnums.RequestType
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterDepotRadio

import java.util.ArrayList

/**
 * Created by tiramakan on 17/01/2016.
 */
class ChoiceOfDepotSaisieStock : BaseFragment(), RecyclerViewAdapterDepotRadio.VHItem.ClickListener {
    internal lateinit var mRecyclerView: RecyclerView
    internal lateinit var adapter: RecyclerViewAdapterDepotRadio
    internal var requestModel: Parcelable? = null
    internal lateinit var requestType: RequestType
    internal var stockModelList: ArrayList<StockRequestModel>? = null
    internal var mybundle=Bundle()
    fun setSelectedItem(itemName: String?) {
        if (itemName != null) {
            val depotPosition = adapter.getPositionByName(itemName)
            if (depotPosition > -1)
                adapter.setSelection(depotPosition)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)

    }

    override fun doBack(): Boolean {
        return true
    }

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.depot_item_list_radio, container, false)
        val searchText = view.findViewById<View>(R.id.searchText) as EditText
        searchText.setOnKeyListener { _, _, _ ->
            val depotUIModel = DepotUIModel.getDepotStartingWith(serviceProvider.service, searchText.text.toString())
            setSelectedItem(depotUIModel.name)
            mRecyclerView.layoutManager!!.scrollToPosition(adapter.getPositionByName(depotUIModel.name.toString()))
            false
        }
        mRecyclerView = view.findViewById<View>(R.id.my_recycler_view) as RecyclerView
        mRecyclerView.setHasFixedSize(false)
        adapter = context?.let { RecyclerViewAdapterDepotRadio(it,DepotUIModel.getList(serviceProvider.service), this) }!!
        mRecyclerView.adapter = adapter

        if (myPreferences.depot != "")
            setSelectedItem(myPreferences.depot)

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

        val depotChoisi = mybundle.getString(Constants.Extra.NOM_DEPOT_TAG).toString()?:""
        if (!depotChoisi.equals("null"))
            setSelectedItem(depotChoisi)
        myParent.setTitle("Saisie stocks")

        return view

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
//            editStockUI.arguments = bundle
//            addFragment(editStockUI)
            mybundle.putString(Constants.Extra.NOM_DEPOT_TAG, adapter.selectedItem?.name.toString())

            val choiceProduits = ChoiceOfProduitReqStock()
                choiceProduits.arguments = mybundle
                addFragment(choiceProduits)


        } else
            bus.post("aucun dépot sélectionné ")
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
