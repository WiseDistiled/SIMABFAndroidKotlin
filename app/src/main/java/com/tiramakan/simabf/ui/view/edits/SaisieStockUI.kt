package com.tiramakan.simabf.ui.view.edits

/**
 * Created by tiramakan on 17/01/2016.
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.github.clans.fab.FloatingActionButton

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.beansProviders.MesureProvider
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.constants.Constants.Extra.LAST_SAVED_STOCK_TAG
import com.tiramakan.simabf.core.modelView.PickedDate
import com.tiramakan.simabf.core.modelView.PriceToUI
import com.tiramakan.simabf.core.modelView.StockToUI
import com.tiramakan.simabf.core.models.realm.Depot
import com.tiramakan.simabf.core.models.realm.Marche
import com.tiramakan.simabf.core.models.savers.ISaverPrice
import com.tiramakan.simabf.core.models.savers.ISaverStock
import com.tiramakan.simabf.databinding.SaisiePrixBinding
import com.tiramakan.simabf.databinding.SaisieStockBinding
import com.tiramakan.simabf.ui.view.Lists.ListPricesUI
import com.tiramakan.simabf.ui.view.Lists.ListStocksUI
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.utils.Binding.BindingComponent
import org.parceler.Parcels


import javax.inject.Inject

/**
 * Created by tiramakan on 17/01/2016.
 */
//Wire the layout to the step
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SaisieStockUI : BaseFragment() {

    var stockSaver: ISaverStock? = null
        @Inject set
    var mesuresProvider: MesureProvider? = null
        @Inject set
    val datePicked = PickedDate()
    protected lateinit var mainView: View
    protected var depot: Depot? = null
    protected var depotChoisi: String? = ""
    protected var produitChoisi: String? = ""

    protected lateinit var stockToUI: StockToUI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)
        if (arguments != null) {
            depotChoisi = arguments?.getString(Constants.Extra.NOM_DEPOT_TAG).toString()
            produitChoisi = arguments?.getString(Constants.Extra.NOM_PRODUIT_TAG).toString()
        }



        if (savedInstanceState == null) {
            stockToUI = StockToUI()
            stockToUI.depot = depotChoisi.toString()
            stockToUI.produit = produitChoisi.toString()

        }else
        {
            stockToUI = Parcels.unwrap(savedInstanceState.getParcelable(LAST_SAVED_STOCK_TAG))

        }



    }

    private fun validate(): Boolean {
        stockToUI.date=datePicked.date
        if (stockToUI.isValid) {
            stockSaver?.saveRow(stockToUI)
            addFragment(ListStocksUI())
            return true
        } else {
            bus.post("Veuillez vérifier que vous avez saisi toutes les informations obligatoires")
            return false
        }
    }

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        DataBindingUtil.setDefaultComponent(BindingComponent())
        val binding = DataBindingUtil.inflate<SaisieStockBinding>(inflater, R.layout.saisie_stock, container, false)

        mainView = binding.root

        stockToUI.mesuresProvider= this.mesuresProvider

        binding.pickedDate = datePicked
        binding.stockToUI = stockToUI

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LAST_SAVED_STOCK_TAG, Parcels.wrap(stockToUI))
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

    override fun doBack(): Boolean {

        return true

    }


}
