package com.tiramakan.simabf.ui.view.Lists

import android.app.AlertDialog
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.clans.fab.FloatingActionButton

import com.squareup.otto.Subscribe
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.beansProviders.ProduitProvider
import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.StockListToSMS
import com.tiramakan.simabf.core.models.notifiers.PriceSended
import com.tiramakan.simabf.core.models.realm.Stock
import com.tiramakan.simabf.core.modelView.StockForUI
import com.tiramakan.simabf.core.modelView.StockForUIList
import com.tiramakan.simabf.core.models.notifiers.StockSended
import com.tiramakan.simabf.ui.view.ChoiceOfDepotSaisieStock
import com.tiramakan.simabf.ui.view.Welcome
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.recyclers.ContextMenuRecyclerView
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterStock

import java.io.IOException
import java.util.ArrayList

import javax.inject.Inject

import io.realm.RealmResults


/**
 * Created by Ratan on 7/29/2015.
 */
class ListStocksUI : BaseFragment(), RecyclerViewAdapterStock.VHItem.ClickListener {
    internal var produitProvider: ProduitProvider? = null
    @Inject set
    protected lateinit var adapter: RecyclerViewAdapterStock
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.stock_ui, null)

        val recyclerView = view.findViewById<View>(R.id.stockUIRecycler) as ContextMenuRecyclerView
        val context = context
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = context?.let { RecyclerViewAdapterStock(it, realm.let { it1 -> StockForUIList.getListFromDBNotSended(it1) }, this) }!!
        recyclerView.adapter = adapter
        setHasOptionsMenu(true)
        registerForContextMenu(recyclerView)
        isSended = false

        val  ajouterFAB =  view.findViewById(R.id.fabAjouterStock) as FloatingActionButton;
        ajouterFAB.setOnClickListener { myParent.addFragment(ChoiceOfDepotSaisieStock()) }

        val  publierFAB =  view.findViewById(R.id.fabRetourAuMenu) as FloatingActionButton;
        publierFAB.setOnClickListener { myParent.addFragment(Welcome()) }

        return view
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        if (v.id == R.id.stockUIRecycler) {
            val inflater = requireActivity().menuInflater
            inflater.inflate(R.menu.menu_contextual, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as ContextMenuRecyclerView.RecyclerContextMenuInfo
        onItemClicked(info.position)
        when (item.itemId) {

            R.id.action_delete -> {
                // remove stuff here

                confirmDeleteDialog(info.position)
                return true
            }
            R.id.action_send -> {
                if (adapter.getElementAt(info.position) != null) {
                    bus.post("debut envoie des stocks ")
                    val stock = realm.let { StockForUIList.getRealmStockById(it, adapter.getElementAt(info.position)!!.id!!) }
                    try {

                        if (stock != null) {
                            serviceProvider.service.sendStock(stock)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                        bus.post("Echec de l'envoi , cause : " + e.message)
                        return false
                    }

                } else
                    return false
                // remove stuff here
                serviceProvider.service.setStockSended(adapter.getElementAt(info.position))
                realm.let { StockForUIList.getListFromDBNotSended(it) }.let { adapter.setItems(it) }

                return true
            }
            R.id.action_sended -> {
                serviceProvider.service.setStockSended(adapter.getElementAt(info.position))
                realm.let { StockForUIList.getListFromDBNotSended(it) }.let { adapter.setItems(it) }
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private fun confirmDeleteDialog(position: Int) {

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setMessage("Confirmez-vous la suppression  ?")
        builder.setPositiveButton("Oui") { _, _ ->
            //if user pressed "yes", then he is allowed to exit from application
            serviceProvider.service.removeStock(adapter.getElementAt(position))
            adapter.deleteSelection(position)
        }
        builder.setNegativeButton("Non") { dialog, _ ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId
        when (id) {
            R.id.action_send ->

                sendStocks()
        }
        return true
    }

    @Subscribe
    fun listeningEndOfSending(stockSended: StockSended) {
        val newList = realm.let { StockForUIList.getListFromDBNotSended(it) }
        newList.let { adapter.setItems(it) }
        bus.post(stockSended.message)
    }

    override fun doBack(): Boolean {
        return true
    }

    private fun sendStocks() {


        val stocks = realm.let { StockForUIList.getRealmStocksNotSended(it) }
        if (serviceProvider.service.networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            for (stock in stocks) {
                try {
                    if (stock.balance>0)
                    serviceProvider.service.sendStock(stock)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        } else {
            if (myPreferences.sendRequestBySMS || myPreferences.forceTransportSMS) {
                val depots = ArrayList<String>()
                for (stock in stocks) {
                    if (!depots.contains(stock.depot!!.code))
                        stock.depot?.code.let {
                            if (it != null) {
                                depots.add(it)
                            }
                        }
                }
                for (depot in depots) {
                    val stockListToSMS = stocks.let { StockListToSMS(it, depot) }
                    val messageToSend = stockListToSMS.message
                    val cansend = messageToSend!!.length <= 160
                    if (cansend) {
                        val builder: AlertDialog.Builder
                        builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
                        builder.setCancelable(false)
                        builder.setMessage("Confirmez-vous l''envoi du message $messageToSend a SIMABF ? Cela peut vous coûter des unités")
                        builder.setPositiveButton("Oui") { _, _ ->
                            //if user pressed "yes", then he is allowed to exit from application
                            serviceProvider.service.confirmSendBySMS(messageToSend)
                            for (stock in stocks) {
                                    realm.executeTransaction{
                                    stock.status = "Sended"
                                    }
                                }
                            bus.post(PriceSended("Stocks envoyés avec succès"))
                        }
                        builder.setNegativeButton("Non") { dialog, _ ->
                            //if user select "No", just cancel this dialog and continue with app
                            dialog.cancel()
                        }
                        val alert = builder.create()
                        alert.show()
                    } else {

                        val builder: AlertDialog.Builder
                        builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
                        builder.setCancelable(false)
                        builder.setMessage("Votre message : $messageToSend du magazin $depot ne peut etre envoye car il depasse les 160 caractères ")
                        builder.setNeutralButton("OK") { dialog, _ -> dialog.cancel() }
                        val alert = builder.create()
                        alert.show()

                    }
                }
            }

        }



        isSended = true
    }

    override fun onItemClicked(position: Int) {
        toggleSelection(position)
    }

    private fun toggleSelection(position: Int) {
        adapter.clearSelection()
        adapter.toggleSelection(position)

    }

}
