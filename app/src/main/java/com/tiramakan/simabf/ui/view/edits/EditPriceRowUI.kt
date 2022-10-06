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

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.beansProviders.MarcheProvider
import com.tiramakan.simabf.bootstrap.beansProviders.MesureProvider
import com.tiramakan.simabf.bootstrap.beansProviders.ProduitProvider
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.PickedDate
import com.tiramakan.simabf.core.modelView.PriceToUI
import com.tiramakan.simabf.core.models.realm.Marche
import com.tiramakan.simabf.core.models.savers.ISaverPrice
import com.tiramakan.simabf.databinding.EditPriceBinding
import com.tiramakan.simabf.ui.view.Lists.ListPricesUI
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.utils.Binding.BindingComponent

import org.parceler.Parcels
import timber.log.Timber

import java.util.ArrayList
import java.util.Date

import javax.inject.Inject

/**
 * Created by tiramakan on 17/01/2016.
 */
//Wire the layout to the step
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EditPriceRowUI : BaseFragment() {

    var priceSaver: ISaverPrice? = null
        @Inject set
    var marcheProvider: MarcheProvider? = null
        @Inject set
    var produitProvider: ProduitProvider? = null
        @Inject set
    var mesuresProvider: MesureProvider? = null
        @Inject set
    val datePicked = PickedDate()

    protected lateinit var mainView: View
    protected var marche: Marche? = null
    protected lateinit var priceToUI: PriceToUI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)
        if (arguments != null) {
            val priceToUIBundle = arguments?.getBundle("priceToUIBundle")
            if (priceToUIBundle != null)
                priceToUI = Parcels.unwrap(priceToUIBundle.getParcelable(Constants.Extra.PRICE_TAG))
        }
    }

    private fun validate(): Boolean {
        if (priceToUI.isValid) {
            priceSaver?.saveRow(priceToUI)
            addFragment(ListPricesUI())
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
        val binding = DataBindingUtil.inflate<EditPriceBinding>(inflater, R.layout.edit_price, container, false)

        mainView = binding.root
        if (arguments != null) {
            priceToUI = Parcels.unwrap(arguments?.getBundle("priceToUIBundle")?.getParcelable(Constants.Extra.PRICE_TAG))
        }

        binding.priceToUI = priceToUI
        binding.pickedDate = datePicked
        priceToUI.mesuresProvider= this.mesuresProvider


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
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
