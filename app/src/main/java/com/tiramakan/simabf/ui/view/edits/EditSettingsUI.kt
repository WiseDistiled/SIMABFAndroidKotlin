package com.tiramakan.simabf.ui.view.edits

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.ListPreference
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.view.MenuItem

import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.beansProviders.RealmServiceProvider
import com.tiramakan.simabf.ui.view.MainActivity
import com.tiramakan.simabf.bootstrap.util.MyPreferences


import java.util.ArrayList

import javax.inject.Inject

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.appcompat.widget.Toolbar
import com.tiramakan.simabf.core.models.realm.*

/**
 * Created by tiramakan on 17/01/2016.
 */
class EditSettingsUI : PreferenceActivity(), Toolbar.OnMenuItemClickListener {
    @Inject
    lateinit var bus: Bus
    @Inject
    lateinit var preferences: MyPreferences

    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        loadHeadersFromResource(R.xml.pref_headers, target)


    }

    override fun isValidFragment(fragmentName: String): Boolean {
        return (MesPreferences::class.java.name == fragmentName
                || SyncPreferenceFragment::class.java.name == fragmentName
                || AuthenticationFragment::class.java.name == fragmentName
                || AdministrationFragment::class.java.name == fragmentName)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return false
    }

    /**
     * {@inheritDoc}
     */


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class MesPreferences : PreferenceFragment() {
        @Inject
        lateinit var realmServiceProvider: RealmServiceProvider

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            BootstrapApplication.component().inject(this)


            setHasOptionsMenu(true)

        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                val homeIntent = Intent(activity, MainActivity::class.java)
                homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(homeIntent)
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class SyncPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_sync)
            setHasOptionsMenu(true)

        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                val homeIntent = Intent(activity, MainActivity::class.java)
                homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(homeIntent)
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class AuthenticationFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_authentification)
            setHasOptionsMenu(true)

        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                startActivity(Intent(activity, MainActivity::class.java))
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class AdministrationFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_administration)
            setHasOptionsMenu(true)

        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                val homeIntent = Intent(activity, MainActivity::class.java)
                homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(homeIntent)
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }
}