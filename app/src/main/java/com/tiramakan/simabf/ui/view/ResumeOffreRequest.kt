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
import android.widget.TextView

import com.squareup.otto.Subscribe
import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.OfferRequestModel
import com.tiramakan.simabf.core.models.notifiers.RequestResponse
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.core.myEnums.RequestType
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.request_responses.RequestResponseUI

import org.parceler.Parcels

import java.io.IOException
import java.util.ArrayList

/**
 * Created by tiramakan on 17/01/2016.
 */
class ResumeOffreRequest : BaseFragment() {
    internal var requestModel: Parcelable? = null
    internal lateinit var requestType: RequestType
    var typeOffreChoisi : String=""
    var produitChoisi : String=""

    @Subscribe
    fun listeningRequest(priceResponse: RequestResponse) {
        val responseFragment = RequestResponseUI()
        val bundle = Bundle()
        bundle.putParcelable(Constants.Extra.REQUEST_RESPONSE_TAG, Parcels.wrap(priceResponse))
        responseFragment.arguments = bundle
        addFragment(responseFragment)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        if (arguments != null) {
            typeOffreChoisi = arguments?.getString(Constants.Extra.TYPE_OFFRE_TAG).toString()
            produitChoisi = arguments?.getString(Constants.Extra.NOM_PRODUIT_TAG).toString()
        }

    }

    private fun sendRequest() {
        try {
            val realm = realmServiceProvider.realm
            val produit = realm.where(Produit::class.java)?.equalTo("nom", produitChoisi)?.findFirst()

            bus.post("Envoie de la requ??te d'Offre ")
            if ((produit != null)  ) {
                serviceProvider.service.sendOfferRequest(OfferRequestModel(produit.code, typeOffreChoisi).request)

            }
            isSended = true
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun doBack(): Boolean {
        return true
    }

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.request_resume, container, false)
        //   setSubTitle(view);
        setSubTitleByList(view)

        setHasOptionsMenu(true)
        return view

    }

    protected fun setSubTitleByList(view: View) {
        var subtitleString = ""
        val subTitleTextView = view.findViewById<View>(R.id.subTitle) as TextView

        subtitleString = subtitleString +  "Type d'offre choisi : " + typeOffreChoisi
        subtitleString = subtitleString + System.getProperty("line.separator")!!
        subtitleString = subtitleString +  "Produit choisi : " + produitChoisi
        subtitleString = subtitleString + System.getProperty("line.separator")!!
        subTitleTextView.text = subtitleString
    }

    protected fun setSubTitle(view: View) {
        var subtitleString = ""
        val subTitleTextView = view.findViewById<View>(R.id.subTitle) as TextView

        subtitleString = subtitleString +  "Type d'offre choisi : " + typeOffreChoisi
        subtitleString = subtitleString + System.getProperty("line.separator")!!
        subtitleString = subtitleString +  "Produit choisi : " + produitChoisi
        subtitleString = subtitleString + System.getProperty("line.separator")!!
        subTitleTextView.text = subtitleString
    }

    override fun onDetach() {
        super.onDetach()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId
        when (id) {
            R.id.action_send ->

                sendRequest()
        }
        return true
    }


}
