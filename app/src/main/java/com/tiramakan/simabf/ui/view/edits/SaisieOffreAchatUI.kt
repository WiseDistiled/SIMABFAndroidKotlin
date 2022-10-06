package com.tiramakan.simabf.ui.view.edits

/**
 * Created by tiramakan on 17/01/2016.
 */
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.beansProviders.*
import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.constants.Constants.Extra.LAST_SAVED_OFFRE_ACHAT_TAG
import com.tiramakan.simabf.core.models.savers.ISaverOffer
import com.tiramakan.simabf.core.modelView.OfferToUI
import com.tiramakan.simabf.core.modelView.PickedDate
import com.tiramakan.simabf.core.models.realm.UserParam
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.utils.Binding.SimpleTextWatcher
import com.tiramakan.simabf.databinding.SaisieOffreAchatBinding
import com.tiramakan.simabf.ui.view.Lists.ListOffersAchatUI
import com.tiramakan.simabf.ui.view.utils.Binding.BindingComponent

import org.parceler.Parcels

import java.util.Calendar

import javax.inject.Inject

/**
 * Created by tiramakan on 17/01/2016.
 */
//Wire the layout to the step
class SaisieOffreAchatUI : BaseFragment() {
    var produitProvider: ProduitProvider? = null
        @Inject set
    var qualityProvider: QualiteProvider? = null
        @Inject set
    var mesuresProvider: MesureProvider? = null
        @Inject set
    var regionsProvider: RegionProvider? = null
        @Inject set
    var iSaverOffer: ISaverOffer? = null
        @Inject set
    lateinit var offerToUI: OfferToUI
    lateinit var userParamProvider: UserParamProvider
        @Inject set
    var produitChoisi: String = ""
    val datePicked = PickedDate()
    protected lateinit var locationManager: LocationManager
        @Inject set
    protected lateinit var mLocationListener: LocationListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)
        if (savedInstanceState == null) {
            offerToUI = OfferToUI()


        }else
        {
            offerToUI = Parcels.unwrap(savedInstanceState.getParcelable(Constants.Extra.LAST_SAVED_OFFRE_VENTE_TAG))

        }
        if (arguments != null) {
            produitChoisi = arguments?.getString(Constants.Extra.NOM_PRODUIT_TAG).toString()

            val priceToUIBundle = arguments?.getBundle("offreAchatToUIBundle")
            if (priceToUIBundle != null) {
                offerToUI = Parcels.unwrap(priceToUIBundle.getParcelable(Constants.Extra.OFFER_TO_UI_BUNDLE))
                produitChoisi = offerToUI.produit?.get().toString()

            }
        }
        offerToUI.produit?.set(produitChoisi.toString())
    }

    //Set your layout here
    @SuppressLint("PrivateResource")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        offerToUI.qualityProvider= this.qualityProvider
        offerToUI.mesuresProvider= this.mesuresProvider
        offerToUI.regionsProvider= this.regionsProvider

        DataBindingUtil.setDefaultComponent(BindingComponent())
        val binding = DataBindingUtil.inflate<SaisieOffreAchatBinding>(inflater, R.layout.saisie_offre_achat, container, false)

        binding.pickedDate = datePicked

        val c = Calendar.getInstance()
        c.time = datePicked.date   // Now use today date.
        c.add(Calendar.DATE, 15) // Adding 5 days
        offerToUI.expirationDate?.set(c.time)
        binding.offre = offerToUI
        binding.produit.text = offerToUI.produit?.get() ?: ""

        offerToUI.produit?.set(binding.produit.toString())
        offerToUI.telephone?.set(myPreferences.login?:"")
        binding.authorPhone.setText(myPreferences.login)
        val qtyTextWatcher = object : SimpleTextWatcher() {
            override fun onTextChanged(newValue: String) {
                offerToUI.quantite?.get()?.let { offerToUI.prixUnitaire?.get()?.times(it) }?.let { offerToUI.montant?.set(it) }
           }
        }
        binding.quantity.addTextChangedListener(qtyTextWatcher)

        val unitpriceTextWatcher = object : SimpleTextWatcher() {
            override fun onTextChanged(newValue: String) {
                if (offerToUI.prixGlobal!! > 0.0)
                    offerToUI.montant?.set(offerToUI.prixGlobal?:0.0)
            }
        }
        binding.unitPrice.addTextChangedListener(unitpriceTextWatcher)
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            bus.post("Verifiez que vous disposez des autorisations necessaires pour utiliser le GPS ou vérifier que le GPS est active")
        } else {
            mLocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    offerToUI.longitude=location.longitude.toString()
                    offerToUI.latitude=location.latitude.toString()
                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

                }

                override fun onProviderEnabled(provider: String) {

                }

                override fun onProviderDisabled(provider: String) {

                }

            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.Location.LOCATION_REFRESH_TIME,
                    Constants.Location.LOCATION_REFRESH_DISTANCE, mLocationListener)
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LAST_SAVED_OFFRE_ACHAT_TAG, Parcels.wrap(offerToUI))

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId
        when (id) {
            R.id.action_next -> {
                offerToUI.offerType?.set("Achat")
                produitChoisi.let { offerToUI.produit?.set(it) }

                if (UIUtils.isValidPhoneNumber(requireContext(), offerToUI.telephone?.get().toString())) {
                    if (offerToUI.validate()) {
                        offerToUI.date?.set(datePicked.date)
                          iSaverOffer?.save(offerToUI)
                           myParent.addFragment(ListOffersAchatUI())

                    } else {
                        bus.post("Veuillez vérifier que vous avez saisi toutes les informations obligatoires")
                    }
                }



            }
        }
        return true
    }
    fun isSIMBetail():Boolean {
        return (userParamProvider.get().reseau=="SIMBETAIL")
    }
    fun isSIMSONAGESS():Boolean {
        return (userParamProvider.get().reseau=="SIMSONAGESS")
    }
    fun isSIMPFNL():Boolean {
        return (userParamProvider.get().reseau=="SIMPFNL")
    }
    fun isSIMDGPER():Boolean {
        return (userParamProvider.get().reseau=="SIMDGPER")
    }
    override fun doBack(): Boolean {

        return true

    }

    companion object {
        val OFFER_TO_UI = "OFFER_TO_UI"
    }

}
