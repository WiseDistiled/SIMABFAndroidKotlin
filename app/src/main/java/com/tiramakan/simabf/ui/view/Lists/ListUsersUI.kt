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
import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.core.modelView.UsersToUIList
import com.tiramakan.simabf.core.models.notifiers.UserSended
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.recyclers.ContextMenuRecyclerView
import com.tiramakan.simabf.ui.view.recyclers.RecyclerViewAdapterUsers
import io.realm.Realm

import java.io.IOException


/**
 * Created by Ratan on 7/29/2015.
 */
class ListUsersUI : BaseFragment(), RecyclerViewAdapterUsers.VHItem.ClickListener {

    protected lateinit var mainView: View
    protected lateinit var adapter: RecyclerViewAdapterUsers
    override val realm: Realm
        get() = realmServiceProvider.realm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.users_ui, null)
        val recyclerView = mainView.findViewById<View>(R.id.recycler) as ContextMenuRecyclerView
        val context = context
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = context?.let { RecyclerViewAdapterUsers(it, realm.let { it1 -> UsersToUIList.getListFromDBNotSended(it1) }, this) }!!
        recyclerView.adapter = adapter
        registerForContextMenu(recyclerView)
        setHasOptionsMenu(true)
        isSended = false

        return mainView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)


    }

    private fun confirmDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setMessage("Confirmez-vous la suppression  ?")
        builder.setPositiveButton("Oui") { _, _ ->
            //if user pressed "yes", then he is allowed to exit from application
            serviceProvider.service.removeUser(adapter.getElementAt(position))
            adapter.deleteSelection(position)
        }
        builder.setNegativeButton("Non") { dialog, _ ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()

    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        if (v.id == R.id.newsUIRecycler) {
            val inflater = requireActivity().menuInflater
            inflater.inflate(R.menu.menu_contextual, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as ContextMenuRecyclerView.RecyclerContextMenuInfo
        onItemClicked(info.position)
        when (item.itemId) {

            R.id.action_send -> if (adapter.getElementAt(info.position) != null) {

                val user = realm.let { UsersToUIList.getRealmUserById(it, adapter.getElementAt(info.position)!!.id) }
                bus.post("debut envoie des utilisateurs ")
                try {
                    if (user != null) {
                        serviceProvider.service.sendUser(user)
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
            R.id.action_sended -> {
                // remove stuff here
                serviceProvider.service.setUserSended(adapter.getElementAt(info.position))
                adapter.setItems(realm.let { UsersToUIList.getListFromDBNotSended(it) })

                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when (id) {
            R.id.action_send -> sendUsers()
        }
        return true
    }

    private fun sendUsers() {
        val users = realm.let { UsersToUIList.getRealmUsersNotSended(it) }
        for (user in users) {
            try {
                serviceProvider.service.sendUser(user)
                isSended = true
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    @Subscribe
    fun listeningEndOfSending(userSended: UserSended) {
        val userToUIs = realm.let { UsersToUIList.getListFromDBNotSended(it) }
        adapter.setItems(userToUIs)
        bus.post(userSended.message)
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
