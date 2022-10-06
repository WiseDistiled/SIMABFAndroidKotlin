package com.tiramakan.simabf.ui.view.Lists

import android.app.AlertDialog
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.squareup.otto.Subscribe
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.models.notifiers.OfferSended
import com.tiramakan.simabf.core.modelView.OffersToUIList
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.edits.SaisieOffreAchatUI
import com.tiramakan.simabf.ui.view.edits.SaisieOffreVenteUI
import com.tiramakan.simabf.ui.view.recyclers.ContextMenuRecyclerView
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterOffer
import org.parceler.Parcels

import java.io.IOException


/**
 * Created by Ratan on 7/29/2015.
 */
class ListOffersAchatUI : BaseFragment(), RecyclerViewAdapterOffer.VHItem.ClickListener {
    internal lateinit var adapter: RecyclerViewAdapterOffer

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId
        when (id) {
            R.id.action_send -> sendOffersAchat()
        }
        return true
    }

    private fun confirmDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setMessage("Confirmez-vous la suppression  ?")
        builder.setPositiveButton("Oui") { _, _ ->
            //if user pressed "yes", then he is allowed to exit from application
            serviceProvider.service.removeOffer(adapter.getElementAt(position))
            adapter.deleteSelection(position)
        }
        builder.setNegativeButton("Non") { dialog, _ ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()

    }


    @Subscribe
    fun listeningEndOfSending(offerSended: OfferSended) {
        val newList = realm.let { OffersToUIList.getListFromDBNotSended(it) }
        newList.let { adapter.setItems(it) }
        bus.post(offerSended.message)
    }

    private fun sendOffersAchat() {
        val offers = realm.let { OffersToUIList.getRealmOffersNotSended(it) }
        for (offer in offers) {
            try {
                serviceProvider.service.sendOfferAchat(offer)


            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        isSended = true
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        if (v.id == R.id.offerAchatUIRecycler) {
            val inflater = requireActivity().menuInflater
            inflater.inflate(R.menu.menu_contextual, menu)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.offre_achat_ui, null)
        val recyclerView = view.findViewById<View>(R.id.offerAchatUIRecycler) as ContextMenuRecyclerView

        val context = context
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerViewAdapterOffer(requireContext(), OffersToUIList.getListFromDBNotSended(realm), this, myPreferences.defaultCurrency)
        recyclerView.adapter = adapter
        registerForContextMenu(recyclerView)
        isSended = false
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as ContextMenuRecyclerView.RecyclerContextMenuInfo
        onItemClicked(info.position)
        when (item.itemId) {

            R.id.action_send -> if (adapter.getElementAt(info.position) != null) {
                bus.post("debut envoie des offres d'achat ")
                val offre = realm.let { adapter.getElementAt(info.position)!!.id?.let { it1 -> OffersToUIList.getRealmOfferById(it, it1) } }
                try {
                    offre.let { it?.let { it1 -> serviceProvider.service.sendOfferAchat(it1) } }
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
                    val offre = realm.let { adapter.getElementAt(info.position)!!.id?.let { it1 -> OffersToUIList.getRealmOfferById(it, it1) } }

                    val bundle = Bundle()
                    val bundleRequestModelList = Bundle()
                    if (offre !== null) {
                        bundleRequestModelList.putParcelable(Constants.Extra.OFFER_TO_UI_BUNDLE,
                                Parcels.wrap( adapter.getElementAt(info.position)!!.id?.let { it1 -> OffersToUIList.getOfferToUIById(realm, it1) } ))
                        bundle.putBundle("offreAchatToUIBundle", bundleRequestModelList)
                        val editOffre : Fragment
                        editOffre = SaisieOffreAchatUI()

                        editOffre.arguments = bundle
                        addFragment(editOffre)
                        return true
                    }  else return false
                } else return false
            }
            R.id.action_sended -> {
                // remove stuff here
                serviceProvider.service.setOfferSended(adapter.getElementAt(info.position))
                adapter.setItems(realm.let { OffersToUIList.getListFromDBNotSended(it) })

                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }


    override fun doBack(): Boolean {
        return true
    }

    override fun onItemClicked(position: Int) {
        toggleSelection(position)
    }

    private fun toggleSelection(position: Int) {
        adapter.clearSelection()
        adapter.toggleSelection(position)

    }

}
