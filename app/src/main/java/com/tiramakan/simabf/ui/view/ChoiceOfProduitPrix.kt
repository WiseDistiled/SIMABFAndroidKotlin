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
import com.tiramakan.simabf.core.modelView.ProduitUIModel
import com.tiramakan.simabf.core.myEnums.RequestType
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.edits.SaisiePrixUI
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterProduitRadio

/**
 * Created by tiramakan on 17/01/2016.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ChoiceOfProduitPrix : BaseFragment(), RecyclerViewAdapterProduitRadio.VHItem.ClickListener {
    internal lateinit var mRecyclerView: RecyclerView
    internal lateinit var adapter: RecyclerViewAdapterProduitRadio
    internal var marcheChoisi: String = ""
    internal var typePrixChoisi: String = ""
    internal var mybundle=Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        if (arguments != null) {
           marcheChoisi = arguments?.getString(Constants.Extra.NOM_MARCHE_TAG).toString()
           typePrixChoisi = arguments?.getString(Constants.Extra.TYPE_PRIX_TAG).toString()


        }

        setHasOptionsMenu(true)
    }

    override fun doBack(): Boolean {
        return true
    }

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.produit_item_list_radio, container, false)
        val searchText = view.findViewById<View>(R.id.searchText) as EditText

        searchText.setOnKeyListener { _, _, _ ->
            val produitUIModel = ProduitUIModel.getProduitStartingWith(serviceProvider.service, searchText.text.toString())
            setSelectedItem(produitUIModel.name)
            mRecyclerView.layoutManager!!.scrollToPosition(adapter.getPositionByName(produitUIModel.name.toString()))

            false
        }
        mRecyclerView = view.findViewById<View>(R.id.my_recycler_view) as RecyclerView
        mRecyclerView.setHasFixedSize(false)

        if (UIUtils.isPaysage(requireContext()))
            mRecyclerView.layoutManager = GridLayoutManager(context, 5)
        else
            mRecyclerView.layoutManager = GridLayoutManager(context, 3)
        val title = "Saisie prix "

        myParent.setTitle(title)
        adapter = RecyclerViewAdapterProduitRadio(requireContext(),ProduitUIModel.getList(serviceProvider.service), this)
        mRecyclerView.adapter = adapter
        val produitChoisi = mybundle.getString(Constants.Extra.NOM_PRODUIT_TAG).toString()
        if (!produitChoisi.equals("null"))
            setSelectedItem(produitChoisi)
        setSubTitle(view)
        return view

    }

    fun setSelectedItem(itemName: String?) {
        if (itemName != null) {
            val produitPosition = adapter.getPositionByName(itemName)
            if (produitPosition > -1)
                adapter.setSelection(produitPosition)
        }
    }

    protected fun setSubTitle(view: View) {
        var subtitleString = "Type Prix : " + typePrixChoisi
        val subTitleTextView = view.findViewById<View>(R.id.subTitle) as TextView

            subtitleString = subtitleString + System.getProperty("line.separator")!!
        subtitleString = subtitleString +  "Marché choisi : " + marcheChoisi
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
            mybundle.putString(Constants.Extra.NOM_PRODUIT_TAG, adapter.selectedItem?.name.toString())
            mybundle.putString(Constants.Extra.NOM_MARCHE_TAG, marcheChoisi)
            mybundle.putString(Constants.Extra.TYPE_PRIX_TAG, typePrixChoisi)


                val editPrice = SaisiePrixUI()
                editPrice.arguments = mybundle
                addFragment(editPrice)

        } else
            bus.post("aucun produit sélectionné ")
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
