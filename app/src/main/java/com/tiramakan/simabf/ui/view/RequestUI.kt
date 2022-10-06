package com.tiramakan.simabf.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.squareup.otto.Subscribe
import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.OfferRequestModel
import com.tiramakan.simabf.core.modelView.PriceRequestModel
import com.tiramakan.simabf.core.modelView.StockRequestModel
import com.tiramakan.simabf.core.myEnums.RequestType
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment

/**
 * Created by Ratan on 7/27/2015.
 */
class RequestUI : BaseFragment(), View.OnClickListener {

    // TODO: Customize parameters
    protected lateinit var dashboard_prix: Button
    protected lateinit var dashboard_offre_achat: Button
    protected lateinit var dashboard_offre_vente: Button
    protected lateinit var dashboard_stocks: Button
    internal var mybundle=Bundle()
    override fun onResume() {
        super.onResume()
        mListener.showDrawerToggle(false)
        myParent.setTitle("Requettes")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /**
         * Inflate tab_layout and setup Views.
         */
        val view = inflater.inflate(R.layout.request_layout, null)
        dashboard_prix = view.findViewById<View>(R.id.dashboard_prix) as Button
        dashboard_offre_achat = view.findViewById<View>(R.id.dashboard_offre_achat) as Button
        dashboard_offre_vente = view.findViewById<View>(R.id.dashboard_offre_vente) as Button

        dashboard_stocks = view.findViewById<View>(R.id.dashboard_stocks) as Button

        dashboard_prix.setOnClickListener(this)
        dashboard_stocks.setOnClickListener(this)
        dashboard_offre_achat.setOnClickListener(this)
        dashboard_offre_vente.setOnClickListener(this)
        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)


    }


    override fun doBack(): Boolean {
        return true
    }

    override fun onClick(v: View) {

        if (v.id == R.id.dashboard_prix) {
            val fragment = ChoiceOfMarcheReqPrix()
            addFragment(fragment)
        } else if (v.id == R.id.dashboard_offre_achat) {
            mybundle.putString(Constants.Extra.TYPE_OFFRE_TAG, "Achat")
            val choiceOfProduit = ChoiceOfProduitReqOffre()
            choiceOfProduit.arguments = mybundle
            addFragment(choiceOfProduit)
        }  else if (v.id == R.id.dashboard_offre_vente) {
            mybundle.putString(Constants.Extra.TYPE_OFFRE_TAG, "Vente")
            val choiceOfProduit = ChoiceOfProduitReqOffre()
            choiceOfProduit.arguments = mybundle
            addFragment(choiceOfProduit)
        }
        else if (v.id == R.id.dashboard_stocks) {
            val fragment = ChoiceOfDepotReqStock()
            addFragment(fragment)
        }
    }


}
