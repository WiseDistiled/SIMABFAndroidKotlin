package com.tiramakan.simabf.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.ui.view.Lists.*
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment

/**
 * Created by Ratan on 7/27/2015.
 */
class ListsUI : BaseFragment(), View.OnClickListener {

    // TODO: Customize parameters
    protected lateinit var dashboard_list_prix: Button
    protected lateinit var dashboard_list_offres_vente: Button
    protected lateinit var dashboard_list_offres_achat: Button
    protected lateinit var dashboard_list_stocks: Button
    protected lateinit var dashboard_list_users: Button
    protected lateinit var dashboard_list_etalonnage: Button

    override fun onResume() {
        super.onResume()
        mListener.showDrawerToggle(false)
        myParent.setTitle("Listes")


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /**
         * Inflate tab_layout and setup Views.
         */
        val view = inflater.inflate(R.layout.lists_layout, null)
        dashboard_list_prix = view.findViewById<View>(R.id.dashboard_list_prix) as Button
        dashboard_list_offres_vente = view.findViewById<View>(R.id.dashboard_list_offres_vente) as Button
        dashboard_list_offres_achat = view.findViewById<View>(R.id.dashboard_list_offres_achat) as Button

        dashboard_list_stocks = view.findViewById<View>(R.id.dashboard_list_stocks) as Button
        dashboard_list_users = view.findViewById<View>(R.id.dashboard_list_users) as Button
        dashboard_list_etalonnage= view.findViewById<View>(R.id.dashboard_list_etalonnage) as Button
        dashboard_list_prix.setOnClickListener(this)
        dashboard_list_offres_vente.setOnClickListener(this)
        dashboard_list_offres_achat.setOnClickListener(this)
        dashboard_list_stocks.setOnClickListener(this)
        dashboard_list_etalonnage.setOnClickListener(this)
        dashboard_list_users.setOnClickListener(this)
        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)


    }

//    @Subscribe
//    fun requestInstrumentation(priceRequestModel: PriceRequestModel) {
//
//    }

    override fun doBack(): Boolean {
        return true
    }

    override fun onClick(v: View) {

        val id = v.id
        when (id) {
            R.id.dashboard_list_prix -> showFragment(ListPricesUI())
            R.id.dashboard_list_offres_vente -> showFragment(ListOffersVenteUI())
            R.id.dashboard_list_offres_achat -> showFragment(ListOffersAchatUI())
            R.id.dashboard_list_stocks -> showFragment(ListStocksUI())
            R.id.dashboard_list_users -> showFragment(ListUsersUI())
            R.id.dashboard_list_etalonnage -> showFragment(ListEtalonnagesUI())


        }

    }


}
