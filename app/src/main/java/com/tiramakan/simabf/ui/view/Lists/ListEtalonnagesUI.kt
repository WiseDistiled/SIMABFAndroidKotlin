package com.tiramakan.simabf.ui.view.Lists

import android.app.AlertDialog
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.squareup.otto.Subscribe
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.EtalonnageForUIList
import com.tiramakan.simabf.core.models.notifiers.EtalonnageSended
import com.tiramakan.simabf.core.models.notifiers.StockSended
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.edits.EditEtalonnageUI
import com.tiramakan.simabf.ui.view.recyclers.ContextMenuRecyclerView
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterEtalonnage

import java.io.IOException

/**
 * Created by Ratan on 7/29/2015.
 */
class ListEtalonnagesUI : BaseFragment(), RecyclerViewAdapterEtalonnage.VHItem.ClickListener {

    protected lateinit var adapter: RecyclerViewAdapterEtalonnage
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.etalonnage_ui, null)

        val recyclerView = view.findViewById<View>(R.id.etalonnageUIRecycler) as ContextMenuRecyclerView
        val context = context
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = context?.let { RecyclerViewAdapterEtalonnage(it, realm.let { it1 -> EtalonnageForUIList.getListFromDBNotSended(it1) }, this) }!!
        recyclerView.adapter = adapter
        setHasOptionsMenu(true)
        registerForContextMenu(recyclerView)
        isSended = false
        return view
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v.id == R.id.etalonnageUIRecycler) {
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
            R.id.action_edit -> {
                val editEtalonnage= EditEtalonnageUI()
                  addFragment(editEtalonnage)
                        return true

            }
            R.id.action_send -> {
                if (adapter.getElementAt(info.position) != null) {
                    bus.post("debut envoie des étalonnages ")
                    val etalonnage = realm.let { EtalonnageForUIList.getRealmEtalonnageById(it, adapter.getElementAt(info.position)?.id!!) }
                    try {

                        if (etalonnage != null) {
                            serviceProvider.service.sendEtalonnage(etalonnage)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                        bus.post("Echec de l'envoi , cause : " + e.message)
                        return false
                    }

                } else
                    return false
                // remove stuff here
                serviceProvider.service.setEtalonnageSended(adapter.getElementAt(info.position))
                realm.let { EtalonnageForUIList.getListFromDBNotSended(it) }.let { adapter.setItems(it) }

                return true
            }
            R.id.action_sended -> {
                serviceProvider.service.setEtalonnageSended(adapter.getElementAt(info.position))
                realm.let { EtalonnageForUIList.getListFromDBNotSended(it) }.let { adapter.setItems(it) }
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
            serviceProvider.service.removeEtalonnage(adapter.getElementAt(position))
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
        val id = item.itemId
        when (id) {
            R.id.action_send ->
                sendEtalonnages()
        }
        return true
    }

    @Subscribe
    fun listeningEndOfSending(etalonnageSended: EtalonnageSended) {
        val newList = realm.let { EtalonnageForUIList.getListFromDBNotSended(it) }
        newList.let { adapter.setItems(it) }
        bus.post(etalonnageSended.message)
    }

    override fun doBack(): Boolean {
        return true
    }

    private fun sendEtalonnages() {
        val etalonnages = realm.let { EtalonnageForUIList.getRealmEtalonnagesNotSended(it) }
        if (serviceProvider.service.networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {

           if (serviceProvider.service.login()) {
               for (etalonnage in etalonnages) {
                       if (etalonnage.valeur > 0)
                           serviceProvider.service.sendEtalonnage(etalonnage)


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
