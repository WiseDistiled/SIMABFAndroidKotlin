package com.tiramakan.simabf.ui.view.Lists

import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.clans.fab.FloatingActionButton

import com.squareup.otto.Subscribe
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.PriceListToSMS
import com.tiramakan.simabf.core.models.realm.Marche
import com.tiramakan.simabf.core.models.notifiers.PriceSended
import com.tiramakan.simabf.core.modelView.PriceToUIList
import com.tiramakan.simabf.ui.view.ChoiceOfTypePrix
import com.tiramakan.simabf.ui.view.Welcome
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.edits.EditPriceRowUI
import com.tiramakan.simabf.ui.view.edits.SaisiePrixUI
import com.tiramakan.simabf.ui.view.recyclers.ContextMenuRecyclerView
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterPrice

import java.io.IOException
import java.util.ArrayList

import io.realm.Realm
import org.parceler.Parcels


class ListPricesUI : BaseFragment(), RecyclerViewAdapterPrice.VHItem.ClickListener {

    protected lateinit var marche: Marche
    protected lateinit var mainView: View
    protected lateinit var adapter: RecyclerViewAdapterPrice

    private val isPortrait: Boolean
        get() = requireActivity().resources.configuration.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    override val realm: Realm
        get() = realmServiceProvider.realm

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.price_ui, null)
        val recyclerView = mainView.findViewById<View>(R.id.priceUIRecycler) as ContextMenuRecyclerView
        val context = context
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = context?.let { RecyclerViewAdapterPrice(it, realm.let { it1 -> myPreferences.let { it2 -> PriceToUIList.getListFromDBNotSended(it1, it2) } }, this) }!!
        recyclerView.adapter = adapter
        registerForContextMenu(recyclerView)
        setHasOptionsMenu(true)
        isSended = false
        val  ajouterFAB =  mainView.findViewById(R.id.fabAjouterPrix) as FloatingActionButton;
        ajouterFAB.setOnClickListener { myParent.addFragment(ChoiceOfTypePrix()) }

        val  publierFAB =  mainView.findViewById(R.id.fabRetourAuMenu) as FloatingActionButton;
        publierFAB.setOnClickListener { myParent.addFragment(Welcome()) }



        return mainView
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        if (v.id == R.id.priceUIRecycler) {
            val inflater = requireActivity().menuInflater
            inflater.inflate(R.menu.menu_contextual, menu)
        }
    }

    @Subscribe
    fun listeningEndOfSending(priceSended: PriceSended) {
        val newList = myPreferences.let { realm.let { it1 -> PriceToUIList.getListFromDBNotSended(it1, it) } }
        newList.let { it.let { it1 -> adapter.setItems(it1) } }
        bus.post(priceSended.message)

    }

    private fun sendPrices() {
        val prices = realm.let { PriceToUIList.getRealmPricesNotSended(it) }
        if (serviceProvider.service.networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            for (price in prices) {
                try {
                    serviceProvider.service.sendPrice(price)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        } else {
            if (myPreferences.sendRequestBySMS || myPreferences.forceTransportSMS) {
                val marches = ArrayList<String>()
                for (price in prices) {
                    if (!marches.contains(price.marche?.code))
                        marches.add(price.marche?.code!!)
                }
                for (marche in marches) {
                    val priceListToSMS = PriceListToSMS(prices, marche)
                    val messageToSend = priceListToSMS.messageSMS
                    val cansend = messageToSend!!.length <= 160
                    if (cansend) {
                        val builder: AlertDialog.Builder
                        builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
                        builder.setCancelable(false)
                        builder.setMessage("Confirmez-vous l''envoi du message $messageToSend a SIMABF ? Cela peut vous coûter des unités")
                        builder.setPositiveButton("Oui") { _, _ ->
                            //if user pressed "yes", then he is allowed to exit from application
                            serviceProvider.service.confirmSendBySMS(messageToSend)
                            for (price in prices) {
                                realm.executeTransaction {
                                price.status = "Sended"
                                }
                            }
                            bus.post(PriceSended("Prix envoyés avec succès"))
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
                        builder.setMessage("Votre message : $messageToSend du marche $marche ne peut etre envoye car il depasse les 160 caractères ")
                        builder.setNeutralButton("OK") { dialog, _ -> dialog.cancel() }
                        val alert = builder.create()
                        alert.show()

                    }
                }
            }

        }
        isSended = true


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)

    }


    override fun doBack(): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId
        when (id) {
            R.id.action_send -> sendPrices()

        }
        return true
    }

    override fun onItemClicked(position: Int) {
        toggleSelection(position)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as ContextMenuRecyclerView.RecyclerContextMenuInfo
        onItemClicked(info.position)
        when (item.itemId) {
            R.id.action_send -> if (adapter.getElementAt(info.position) != null) {

                val price = realm.let {
                    adapter.getElementAt(info.position)!!.id?.let { it1 -> PriceToUIList.getRealmPriceById(it, it1) }
                }
                bus.post("debut envoie des prix ")
                try {
                    if (price != null) {
                        serviceProvider.service.sendPrice(price)
                    }
                    return true
                } catch (e: IOException) {
                    e.printStackTrace()
                    bus.post("Echec de l'envoi , cause : " + e.message)
                    return false
                }

            } else
                return false
            R.id.action_delete -> {
                // remove stuff here

                confirmDeleteDialog(info.position)
                return true
            }
            R.id.action_edit -> {
                if (adapter.getElementAt(info.position) != null) {
                    val price = realm.let { adapter.getElementAt(info.position)!!.id?.let { it1 -> PriceToUIList.getRealmPriceById(it, it1) } }

                val bundle = Bundle()
                val bundleRequestModelList = Bundle()
                    if (price !== null) {
                        bundleRequestModelList.putParcelable(Constants.Extra.PRICE_TAG,
                                Parcels.wrap(myPreferences.let { adapter.getElementAt(info.position)!!.id?.let { it1 -> PriceToUIList.getPriceToUIById(realm, it1, it) } }))
                        bundle.putBundle("priceToUIBundle", bundleRequestModelList)
                        val editPrice= SaisiePrixUI()
                        editPrice.arguments = bundle
                        addFragment(editPrice)
                        return true
                    }  else return false
                } else return false
            }
            R.id.action_sended -> {
                // remove stuff here
                serviceProvider.service.setPriceSended(adapter.getElementAt(info.position))
                realm.let { myPreferences.let { it1 -> PriceToUIList.getListFromDBNotSended(it, it1) } }.let { adapter.setItems(it) }

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
            serviceProvider.service.removePrice(adapter.getElementAt(position))
            adapter.deleteSelection(position)
        }
        builder.setNegativeButton("Non") { dialog, _ ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()

    }

    private fun toggleSelection(position: Int) {
        adapter.clearSelection()
        adapter.toggleSelection(position)

    }


}
