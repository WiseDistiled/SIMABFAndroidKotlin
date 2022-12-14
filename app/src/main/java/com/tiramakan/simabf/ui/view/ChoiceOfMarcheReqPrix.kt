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
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.MarcheUIModel
import com.tiramakan.simabf.core.modelView.PriceRequestModel
import com.tiramakan.simabf.core.myEnums.RequestType
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterMarcheRadio
import org.parceler.Parcels

import java.util.ArrayList

/**
 * Created by tiramakan on 17/01/2016.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ChoiceOfMarcheReqPrix : BaseFragment(), RecyclerViewAdapterMarcheRadio.VHItem.ClickListener {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapterMarcheRadio
    internal var mybundle=Bundle()
    fun setSelectedItem(itemName: String?) {
        if (itemName != null ) {
            val marchePosition = adapter.getPositionByName(itemName)
            if (marchePosition > -1)
                adapter.setSelection(marchePosition)
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
        val view = inflater.inflate(R.layout.marche_item_list_radio, container, false)
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
        if (UIUtils.isTablet(requireContext())) {
            if (UIUtils.isPaysage(requireContext())) {
                gridLayoutManager = GridLayoutManager(context, 7)
            } else {
                gridLayoutManager = GridLayoutManager(context, 5)
            }
        } else {
            if (UIUtils.isPaysage(requireContext())) {
                gridLayoutManager = GridLayoutManager(context, 5)
            } else {
                gridLayoutManager = GridLayoutManager(context, 3)
            }
        }

        mRecyclerView.layoutManager = gridLayoutManager


        adapter = RecyclerViewAdapterMarcheRadio(requireContext(),MarcheUIModel.getListForWriting(serviceProvider.service), this)
        mRecyclerView.adapter = adapter
        val title = "Requette de prix "

        myParent.setTitle(title)

            val marcheChoisi = mybundle.getString(Constants.Extra.NOM_MARCHE_TAG).toString()
            if (!marcheChoisi.equals("null"))
                setSelectedItem(marcheChoisi)

       setSubTitle(view)
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
            mybundle.putString(Constants.Extra.NOM_MARCHE_TAG, adapter.selectedItem?.name.toString())

            val choiceOfProduit = ChoiceOfProduitReqPrix()
            choiceOfProduit.arguments = mybundle
            addFragment(choiceOfProduit)
        } else
            bus.post("aucun march?? s??lectionn?? ")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.Extra.NOM_MARCHE_TAG, adapter.selectedItem?.name.toString())

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId
        when (id) {
            R.id.action_next ->

                validate()
        }
        return true
    }

    private fun toggleSelection(position: Int) {
        adapter.clearSelection()
        adapter.toggleSelection(position)

    }
    protected fun setSubTitle(view: View) {
        var subtitleString = ""
        val subTitleTextView = view.findViewById<View>(R.id.subTitle) as TextView
        subtitleString = subtitleString + System.getProperty("line.separator")!!
        subTitleTextView.text = subtitleString
    }
}
