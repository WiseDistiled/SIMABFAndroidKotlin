package com.tiramakan.simabf.ui.view

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.clans.fab.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

import com.squareup.otto.Bus
import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication

import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    internal lateinit var bus: Bus
        @Inject set
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        handleIntent(intent)
        BootstrapApplication.component().inject(this)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            bus.post("Votre requette $query a bien été enregistré, le module de recherche est en cours")
            //use the query to search your data somehow
        }
    }

}
