package com.tiramakan.simabf.ui.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.preference.PreferenceFragmentCompat

import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.AssetImporter
import com.tiramakan.simabf.R

import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [ResetFragment]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class ResetFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
      //  setPreferencesFromResource(R.xml.pref_sync, rootKey);
    }

    internal lateinit var bus: Bus
        @Inject set
    internal var assetImporter: AssetImporter? = null
        @Inject set

    private fun confirmResetDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)
        builder.setMessage("Confirmez-vous la réinitialisation de la base de données, cette opération est irréversible ?")
        builder.setPositiveButton("Oui") { _, _ ->
            //if user pressed "yes", then he is allowed to exit from application
            //  SIMABF.startRemoveService(getContext());
            assetImporter!!.execute()
            bus.post("L'application SIMABF va redémarrer")
            val i = Intent(activity, MainActivity::class.java)  //your class
            startActivity(i)
        }
        builder.setNegativeButton("Non") { dialog, _ ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.reset_frame, container, false)
        val btnReset = view.findViewById<View>(R.id.btnReset) as Button
        btnReset.setOnClickListener { confirmResetDialog() }



        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            startActivity(Intent(activity, MainActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */

}
