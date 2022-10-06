package com.tiramakan.simabf.ui.view.edits

/**
 * Created by tiramakan on 17/01/2016.
 */

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.beansProviders.ProduitProvider
import com.tiramakan.simabf.bootstrap.beansProviders.DepotProvider
import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.StockForUI
import com.tiramakan.simabf.core.modelView.StockViewModel
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.core.models.realm.Depot
import com.tiramakan.simabf.core.models.savers.ISaverStock
import com.tiramakan.simabf.databinding.EditStockBinding
import com.tiramakan.simabf.ui.view.Lists.ListStocksUI
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.utils.Binding.BindingComponent
import com.tiramakan.simabf.ui.view.utils.Binding.Converters

import org.parceler.Parcels

import java.util.ArrayList
import java.util.Date

import javax.inject.Inject

/**
 * Created by tiramakan on 17/01/2016.
 */
class EditStockRowUI : BaseFragment() {

    var depotProvider: DepotProvider? = null
        @Inject set
    var stockSaver: ISaverStock? = null
        @Inject set
    var produitProvider: ProduitProvider? = null
    protected lateinit var mainView: View
    protected var depot: Depot? = null
    lateinit var stockForUI: StockForUI
    protected lateinit var mesures: ArrayList<String>
    var stockViewModel: StockViewModel? = null

        @Inject set
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        mesures = serviceProvider.service.listMesures
        if (arguments != null) {
            val stockForUIBundle = arguments?.getBundle("stockForUIBundle")
            if (stockForUIBundle != null)
                stockForUI = Parcels.unwrap(stockForUIBundle.getParcelable(Constants.Extra.STOCK_TAG))

        }
    }

    override fun setMenuVisibility(visible: Boolean) {
        super.setMenuVisibility(false)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Constants.Extra.STOCK_TAG, Parcels.wrap(stockForUI))
    }

    //Set your layout here
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        DataBindingUtil.setDefaultComponent(BindingComponent())
        val binding = DataBindingUtil.inflate<EditStockBinding>(inflater, R.layout.edit_stock, container, false)
        mainView = binding.root
        setHasOptionsMenu(true)
        if (savedInstanceState != null) {
            stockForUI = Parcels.unwrap(savedInstanceState.getParcelable(Constants.Extra.STOCK_TAG))
        }
        val mesureAdapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, mesures)

        mesureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        val depotadapter = ArrayAdapter(requireActivity(),
                android.R.layout.simple_list_item_1, depotProvider!!.items)
        binding.depot.threshold = 1
        binding.depot.setAdapter(depotadapter)

        binding.mesure.adapter = mesureAdapter

        val produitAdapter = ArrayAdapter(requireActivity(),
                android.R.layout.simple_list_item_1, produitProvider!!.items)
        binding.stockForUI = stockForUI

        val produitTouchClickListener = View.OnTouchListener { v, _ ->
            binding.produit.setText("")
            inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
            false
        }
        binding.mesure.setSelection(Converters.getIndex(binding.mesure, stockForUI.mesure.get()))
        binding.produit.setOnTouchListener(produitTouchClickListener)
        binding.produit.threshold = 1
        binding.produit.setAdapter(produitAdapter)
        binding.produit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val produit = realm.where(Produit::class.java)
                        ?.equalTo("nom", binding.produit.text.toString().trim { it <= ' ' })
                        ?.findFirst()
                produit?.mesure?.code.let { Converters.getIndex(binding.mesure, it?:"") }.let { binding.mesure.setSelection(it) }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.dateSaisie.setText(UIUtils.prettyDateFormat.format(Date()))

        val depotItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ -> inputMethodManager.hideSoftInputFromWindow(binding.depot.applicationWindowToken, 0) }
        val depotTouchClickListener = View.OnTouchListener { v, _ ->
            binding.depot.setText("")
            inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
            false
        }

        binding.depot.onItemClickListener = depotItemClickListener
        binding.depot.setOnTouchListener(depotTouchClickListener)
        inputMethodManager.hideSoftInputFromWindow(binding.depot.applicationWindowToken, 0)

        return mainView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId
        when (id) {
            R.id.action_next -> {
                stockViewModel!!.save()
                myParent.addFragment(ListStocksUI())
            }
        }
        return true
    }
}
