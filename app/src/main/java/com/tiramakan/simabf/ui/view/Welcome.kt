package com.tiramakan.simabf.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.BootstrapServiceProvider
import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.models.notifiers.RequestResponse
import com.tiramakan.simabf.ui.view.edits.*
import com.tiramakan.simabf.ui.view.interfaces.MyActivityInterface
import com.tiramakan.simabf.ui.view.interfaces.OnFragmentInteractionListener
import com.tiramakan.simabf.ui.view.request_responses.RequestResponseUI

import org.parceler.Parcels

import javax.inject.Inject

/**
 * Created by Ratan on 7/27/2015.
 */
class Welcome : Fragment(), View.OnClickListener {
    // TODO: Customize parameter argument names

    protected var serviceProvider: BootstrapServiceProvider? = null
        @Inject set
    protected var bus: Bus? = null
        @Inject set
    protected lateinit var dashboard_prix: Button
    protected lateinit var dashboard_offre_vente: Button
    protected lateinit var dashboard_offre_achat: Button
    protected lateinit var dashboard_stocks: Button
    protected lateinit var dashboard_etalonnage: Button
    protected lateinit var dashboard_enregistrer: Button
    protected lateinit var dashboard_lists: Button
    protected lateinit var dashboard_demandes: Button
    internal lateinit var bundle: Bundle
    private var mListener: OnFragmentInteractionListener? = null
    protected var myParent: MyActivityInterface? = null
    override fun onResume() {
        super.onResume()

        if (mListener != null)
            mListener?.showDrawerToggle(true)
        if (myParent != null)
            myParent?.setTitle("SIMABF")


    }
    override fun onStart() {
        super.onStart()
        bus?.register(this)
    }

    override fun onStop() {
        super.onStop()
        bus?.unregister(this)
    }
    override fun onDetach() {
        super.onDetach()

        // This is VERY important to avoid a memory leak (because mListener is really a reference to an Activity)
        // When the orientation change occurs, onDetach will be called and since the Activity is being destroyed
        // we don't want to keep any references to it
        // When the Activity is being re-created, onAttach will be called and we will get our listener back
        mListener = null
        myParent = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mListener = context as OnFragmentInteractionListener?
        this.myParent = context as MyActivityInterface?
        myParent?.setTitle("SIMABF")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /**
         * Inflate tab_layout and setup Views.
         */
        val view = inflater.inflate(R.layout.welcome_layout, null)
        dashboard_prix = view.findViewById<View>(R.id.dashboard_prix) as Button
        dashboard_offre_vente = view.findViewById<View>(R.id.dashboard_offre_vente) as Button
        dashboard_offre_achat = view.findViewById<View>(R.id.dashboard_offre_achat) as Button
        dashboard_etalonnage = view.findViewById<View>(R.id.dashboard_etalonnage) as Button
        dashboard_stocks = view.findViewById<View>(R.id.dashboard_stocks) as Button
        dashboard_enregistrer = view.findViewById<View>(R.id.dashboard_enregistrer) as Button
        dashboard_lists = view.findViewById<View>(R.id.dashboard_lists) as Button
        dashboard_demandes = view.findViewById<View>(R.id.dashboard_demande) as Button

        dashboard_prix.setOnClickListener(this)
        dashboard_offre_vente.setOnClickListener(this)
        dashboard_offre_achat.setOnClickListener(this)
        dashboard_stocks.setOnClickListener(this)
        dashboard_enregistrer.setOnClickListener(this)
        dashboard_lists.setOnClickListener(this)
        dashboard_demandes.setOnClickListener(this)
        dashboard_etalonnage.setOnClickListener(this)

        return view

    }

    @Subscribe
    fun listeningRequest(reponse: RequestResponse) {
        if (reponse.title == "register") {
            val responseFragment = RequestResponseUI()
            val bundle = Bundle()
            bundle.putParcelable(Constants.Extra.REQUEST_RESPONSE_TAG, Parcels.wrap(reponse))
            responseFragment.arguments = bundle
            myParent?.showFragment(responseFragment)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)


    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.dashboard_prix -> {
                val fragment = ChoiceOfTypePrix()
                myParent?.addFragment(fragment)
            }
            R.id.dashboard_etalonnage -> {
                val fragment = ChoiceOfMarcheEtalonnage()
                myParent?.addFragment(fragment)
            }
            R.id.dashboard_offre_vente -> {
                val choiceOfProduitOffreVente = ChoiceOfProduitOffreVente()
                myParent?.addFragment(choiceOfProduitOffreVente)
            }
            R.id.dashboard_offre_achat -> {
                val choiceOfProduitOffreAchat = ChoiceOfProduitOffreAchat()
                myParent?.addFragment(choiceOfProduitOffreAchat)
            }
            R.id.dashboard_stocks -> {
                    val fragment = ChoiceOfDepotSaisieStock()
                myParent?.addFragment(fragment)
            }

            R.id.dashboard_demande -> myParent?.addFragment(RequestUI())
            R.id.dashboard_enregistrer -> myParent?.addFragment(EditUserUI())

            R.id.dashboard_lists -> myParent?.addFragment(ListsUI())
        }

    }

}
