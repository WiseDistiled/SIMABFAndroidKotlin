package com.tiramakan.simabf.ui.view

/**
 * Created by tiramakan on 17/01/2016.
 */
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.MarcheUIModel
import com.tiramakan.simabf.core.modelView.TypePrixUIModel
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterTypePrixRadio


/**
 * Created by tiramakan on 17/01/2016.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ChoiceOfTypePrix : BaseFragment(), RecyclerViewAdapterTypePrixRadio.VHItem.ClickListener {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapterTypePrixRadio
    internal var mybundle=Bundle()
    fun setSelectedItem(itemName: String?) {
        if (itemName != null ) {
            val typePrixPosition = adapter.getPositionByName(itemName)
            if (typePrixPosition > -1)
                adapter.setSelection(typePrixPosition)
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
        val view = inflater.inflate(R.layout.typeprix_item_list_radio, container, false)
        val marcheEdText = view.findViewById<View>(R.id.searchText) as EditText
        marcheEdText.setOnKeyListener { _, _, _ ->
            val marcheUIModel = MarcheUIModel.getMarcheWith(serviceProvider.service, marcheEdText.text.toString())
            setSelectedItem(marcheUIModel.name)
            mRecyclerView.layoutManager!!.scrollToPosition(adapter.getPositionByName(marcheUIModel.name.toString()))

            false
        }
        mRecyclerView = view.findViewById<View>(R.id.my_recycler_view) as RecyclerView
        mRecyclerView.setHasFixedSize(false)
        val gridLayoutManager: GridLayoutManager
        gridLayoutManager = GridLayoutManager(context, 1)


        mRecyclerView.layoutManager = gridLayoutManager


        adapter = RecyclerViewAdapterTypePrixRadio(requireContext(),TypePrixUIModel.getList(serviceProvider.service), this)
        mRecyclerView.adapter = adapter
        val title = "Saisie des prix "

        myParent.setTitle(title)
        val typePrixChoisi = mybundle.getString(Constants.Extra.TYPE_PRIX_TAG).toString()?:""
        if (!typePrixChoisi.equals("null"))
            setSelectedItem(typePrixChoisi)

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
            mybundle.putString(Constants.Extra.TYPE_PRIX_TAG, adapter.selectedItem?.name.toString())
            val choiceOfProduit = ChoiceOfMarchePrix()
            choiceOfProduit.arguments = mybundle
            addFragment(choiceOfProduit)
        } else
            bus.post("aucun type de prix sélectionné ")
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
