package com.tiramakan.simabf.ui.view

import android.Manifest
import android.app.SearchManager
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView

import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import com.tiramakan.simabf.BuildConfig
import com.tiramakan.simabf.bootstrap.AssetImporter
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.BootstrapServiceProvider

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.models.notifiers.ActionFailed
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.interfaces.MyActivityInterface
import com.tiramakan.simabf.ui.view.interfaces.OnFragmentInteractionListener
import com.tiramakan.simabf.bootstrap.util.MyPreferences

import java.io.File

import javax.inject.Inject

import io.realm.Realm

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.github.clans.fab.FloatingActionButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController
//import com.mobapphome.androidappupdater.tools.AAUpdaterController
import com.tiramakan.simabf.ui.view.Lists.*
import com.tiramakan.simabf.ui.view.edits.*
import com.tiramakan.simabf.ui.view.widgets.ChoiceOfReqMesure

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener, MyActivityInterface {
    @set:Inject
    internal lateinit var bus: Bus
    @set:Inject
    internal lateinit var preferences: MyPreferences
    @set:Inject
    internal lateinit var bootstrapServiceProvider: BootstrapServiceProvider
    @set:Inject
    internal lateinit var assetImporter: AssetImporter

    internal lateinit var mDrawerLayout: DrawerLayout
    internal lateinit var mNavigationView: NavigationView
    protected lateinit var coordinatorLayout: CoordinatorLayout
    protected lateinit var mDrawerToggle: ActionBarDrawerToggle

    private//    UIUtils.isTablet(this);
    val isTablet: Boolean
        get() = true


    public override fun onResume() {
        super.onResume()
        bus.register(this)
            mDrawerToggle.isDrawerIndicatorEnabled = true


    }

    public override fun onDestroy() {
        MAHUpdaterController.end()
        super.onDestroy()
    }

    public override fun onPause() {
        bus.unregister(this)
        super.onPause()


    }


    private fun confirmDBUpdate() {

        val builder:MaterialAlertDialogBuilder
        builder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        builder.setMessage(R.string.AttentionInitialisationBD)
                .setPositiveButton(R.string.yesBtn) { dialog, _ ->
                                                         bootstrapServiceProvider.service.synchroniser()

                                                         }
                .setNegativeButton(R.string.NoBtn) { dialog, _ -> dialog.cancel() }
                .show()
    }

    fun sendDatabase() {

        // init realm
        val realm = Realm.getDefaultInstance()

        try {
            // get or create an "export.realm" file
         val   exportRealmFile = File(this.externalCacheDir, preferences.dbName)

            // if "export.realm" already exists, delete
            if (exportRealmFile.delete()) {
                realm.writeCopyTo(exportRealmFile)
                // init email intent and add export.realm as attachment
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "plain/text"
                intent.putExtra(Intent.EXTRA_EMAIL, preferences.adminMail)
                intent.putExtra(Intent.EXTRA_SUBJECT, "Base de données SIMABFDroid")
                intent.putExtra(Intent.EXTRA_TEXT, "Veuillez trouver ci-joint la base de données de l'application Android ")
                val u = Uri.fromFile(exportRealmFile)
                intent.putExtra(Intent.EXTRA_STREAM, u)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(Intent.createChooser(intent, "Envoie du fichier de base de données"))
                bus.post("Le fichier a été envoyé avec succès à l'adresse " + preferences.adminMail)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            bus.post("Echec de l'envoie du fichier cause " + e.message)
        }

        realm.close()
    }

    private fun logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods

    }

    protected fun resolveContentView() {

        setContentView(R.layout.activity_main)
    }
    fun MainActivity.isHasPermission(vararg permissions: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            permissions.all { singlePermission ->
                applicationContext.checkSelfPermission(singlePermission) == PackageManager.PERMISSION_GRANTED
            }
        else true
    }

    fun MainActivity.askPermission(vararg permissions: String,  requestCode: Int) =
            ActivityCompat.requestPermissions(this, permissions, requestCode)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MAHUpdaterController.init(this,"http://5.189.139.52:8091/restapi/androidAppstatus")
        BootstrapApplication.component().inject(this)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        resolveContentView()

        /**
         * Setup the DrawerLayout and NavigationView
         */
        coordinatorLayout = findViewById<View>(R.id
                .coordinatorLayout) as CoordinatorLayout
        mDrawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        mNavigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerLayout = mNavigationView.getHeaderView(0)

        val appVersionView = headerLayout.findViewById<View>(R.id.appVersionView) as TextView
        appVersionView.text = String.format(resources.getString(R.string.versionTitle), 0, BuildConfig.VERSION_CODE)

        /**
         * Setup Drawer Toggle of the Toolbar
         */
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name)

        //   enabling action bar app icon and behaving it as toggle button
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.toolbarNavigationClickListener = View.OnClickListener { this@MainActivity.onReturnPressed() }

        mDrawerToggle.syncState()

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the Dashboard as the first Fragment
         */

        /**
         * Setup click events on the Navigation View Items.
         */

        if (savedInstanceState == null) {
            val ACCESS_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 5
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA)
            if (!isHasPermission(*permissions))
                askPermission(permissions = *permissions, requestCode = ACCESS_WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
        }

        if (preferences.isLoginAndPasswordOk) {
            showFragment(Welcome())
        } else {
            showFragment(AuthenticateFragment())
        }

        mNavigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            mDrawerLayout.closeDrawers()

            when (menuItem.itemId) {
                android.R.id.home -> {
                    // Don't call finish! Because activity could have been started by an
                    // outside activity and the home button would not operated as expected!
                    val homeIntent = Intent(this@MainActivity, MainActivity::class.java)
                    homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(homeIntent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_item_accueil -> showFragment(Welcome())
                R.id.nav_item_geolocaliser_marche -> showFragment(ChoiceOfMarcheAGeolocaliser())
                R.id.nav_item_geolocaliser_depot -> showFragment(ChoiceOfDepotAGeolocaliser())
                R.id.nav_item_group_partners -> showFragment(PartnersFragment())
                R.id.nav_item_fermer -> confirmDialog()
                R.id.nav_item_auth -> confirmAuthDialog()


                R.id.nav_item_sync -> confirmDBUpdate()
            }//   showFragment(new Dashboard());
            true
        })

        handleIntent(intent)
    }

    private fun confirmAuthDialog() {
        val builder: MaterialAlertDialogBuilder
            builder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        builder.setCancelable(false)
        builder.setMessage("Attention !!! en fonction de votre indicatif téléphonique la base de données va changer, confirmez-vous ? ")
        builder.setPositiveButton(R.string.yesBtn) { _, _ ->
            //if user pressed "yes", then he is allowed to exit from application
            showFragment(AuthenticateFragment())
        }
        builder.setNegativeButton(R.string.NoBtn) { dialog, _ ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()

    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            bus.post("Votre requette $query a bien été enregistré, le module de recherche est en cours")
            //use the query to search your data somehow
        }
    }

    private fun getLastNotNull(list: List<Fragment>): Fragment? {
        for (i in list.indices.reversed()) {
            val frag = list[i]
            return frag
        }
        return null
    }

    private fun getVisibleFragment(list: List<Fragment>): Fragment? {
        for (i in list.indices.reversed()) {
            val frag = list[i]

            if (frag.isVisible)
                return frag
        }
        return null
    }

    internal fun isLastFragmentBeforeHome(frag: Fragment): Boolean {
        val fragmentName = frag.javaClass.name
        return (ListsUI::class.java.name == fragmentName
                || AuthenticateFragment::class.java.name == fragmentName)
    }

    //
    //    @Override
    fun onReturnPressed() {
        onBackPressed()

    }

    override fun onBackPressed() {
        val stackCount = supportFragmentManager.backStackEntryCount
        val frags = supportFragmentManager.fragments
        val lastFrag = getLastNotNull(frags)
        val isFragmentNul = lastFrag == null
        val currentFragment = getVisibleFragment(frags)//getVisible method return current visible fragment
        if (currentFragment != null) {
            if (currentFragment is BaseFragment) {
                if (currentFragment.isSended) {
                    showFragment(Welcome())
                } else {
                    if (stackCount > 0) {
                        if (lastFrag is BaseFragment) {
                            val baseFragment = lastFrag as BaseFragment?
                            if (baseFragment?.doBackListener != null) {
                                if (baseFragment.doBackListener.doBack()) {
                                        supportFragmentManager.popBackStack()
                                        supportFragmentManager.executePendingTransactions()
                                          }
                            } else {
                                supportFragmentManager.popBackStack()
                                supportFragmentManager.executePendingTransactions()
                            }
                        }

                    }
                    if (!isFragmentNul) {
                        if (isLastFragmentBeforeHome(lastFrag!!)) {
                            showFragment(Welcome())
                        }
                    } else
                        confirmDialog()

                }
            }
        }
    }

    private fun confirmExportDialog() {
        val builder: MaterialAlertDialogBuilder
        builder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)

        builder.setCancelable(false)
        builder.setMessage("Confirmez-vous l''envoi de la base SIMABF à l'administrateur ?")
        builder.setPositiveButton(R.string.yesBtn) { _, _ ->
            //if user pressed "yes", then he is allowed to exit from application
            sendDatabase()
        }
        builder.setNegativeButton(R.string.NoBtn) { dialog, _ ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()

    }

    private fun confirmResetDialog() {
        val builder: MaterialAlertDialogBuilder
            builder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        builder.setCancelable(false)
        builder.setMessage("Confirmez-vous la réinitialisation de la base de données, cette opération est irréversible ?")
        builder.setPositiveButton(R.string.yesBtn) { _, _ ->
            //if user pressed "yes", then he is allowed to exit from application
            //  SIMABF.startRemoveService(MainActivity.this);
            assetImporter.execute()
            bus.post("Vous devez quitter l'application et vous reconnecter")

            //    showFragment(new Dashboard());
        }
        builder.setNegativeButton(R.string.NoBtn) { dialog, _ ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()

    }

    private fun confirmDialog() {
        val builder: MaterialAlertDialogBuilder
        builder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        builder.setCancelable(false)
        builder.setMessage("Voulez-vous quitter l''application SIMABF ?")
        builder.setPositiveButton(R.string.yesBtn) { _, _ ->
            //if user pressed "yes", then he is allowed to exit from application
            this@MainActivity.finish()
        }
        builder.setNegativeButton(R.string.NoBtn) { dialog, _ ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val fragmentManager = supportFragmentManager
        val whichFragment = getVisibleFragment(fragmentManager.fragments)//getVisible method return current visible fragment
        val item_send = menu.findItem(R.id.action_send)
        val item_next = menu.findItem(R.id.action_next)
        //        MenuItem item_search = menu.findItem(R.id.search);
        if (whichFragment != null) {
            item_send.isVisible = false
            item_next.isVisible = false
            val shareVisible = whichFragment.javaClass.toString()


            if (shareVisible == ListPricesUI::class.java.toString()
                    || shareVisible == ListOffersVenteUI::class.java.toString()
                    || shareVisible == ListOffersAchatUI::class.java.toString()
                    || shareVisible == EditGPSMarcheUI::class.java.toString()
                    || shareVisible == EditGPSDepotUI::class.java.toString()
                    || shareVisible == ListStocksUI::class.java.toString()
                    || shareVisible == ListUsersUI::class.java.toString()
                    || shareVisible == ResumePriceRequest::class.java.toString()
                    || shareVisible == ResumeStockRequest::class.java.toString()
                    || shareVisible == ResumeOffreRequest::class.java.toString()
                    || shareVisible == ListEtalonnagesUI::class.java.toString()



            ) {
                item_send.isVisible = true
            }

            if ( shareVisible == ChoiceOfProduitPrix::class.java.toString()
                    || shareVisible == ChoiceOfMarchePrix::class.java.toString()
                    || shareVisible == ChoiceOfProduitStock::class.java.toString()
                    || shareVisible == ChoiceOfDepotAGeolocaliser::class.java.toString()
                    || shareVisible == ChoiceOfTypePrix::class.java.toString()
                    || shareVisible == EditStockRowUI::class.java.toString()
                    || shareVisible == EditUserUI::class.java.toString()
                    || shareVisible == SaisiePrixUI::class.java.toString()
                    || shareVisible == EditPriceRowUI::class.java.toString()
                    || shareVisible == ChoiceOfDepotSaisieStock::class.java.toString()
                    || shareVisible == SaisieStockUI::class.java.toString()
                    || shareVisible == ChoiceOfProduitOffreAchat::class.java.toString()
                    || shareVisible == SaisieOffreAchatUI::class.java.toString()
                    || shareVisible == ChoiceOfProduitOffreVente::class.java.toString()
                    || shareVisible == SaisieOffreVenteUI::class.java.toString()
                    || shareVisible == ChoiceOfTypeGeoLocal::class.java.toString()
                    || shareVisible == ChoiceOfMarcheAGeolocaliser::class.java.toString()
                    || shareVisible == ChoiceOfReqMesure::class.java.toString()
                    || shareVisible == ChoiceOfProduitReqPrix::class.java.toString()
                    || shareVisible == ChoiceOfMarcheReqPrix::class.java.toString()
                    || shareVisible == ChoiceOfDepotReqStock::class.java.toString()
                    || shareVisible == ChoiceOfProduitReqStock::class.java.toString()
                    || shareVisible == ChoiceOfTypeOffre::class.java.toString()
                    || shareVisible == ChoiceOfProduitReqOffre::class.java.toString()
                    || shareVisible == ChoiceOfMarcheEtalonnage::class.java.toString()
                    || shareVisible == EditEtalonnageUI::class.java.toString()
          ) {
                item_next.isVisible = true
            }
        }


        return true
    }


    @Subscribe
    fun printOnSnackBar(message: String) {
        val snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.WHITE)
        val snackbarView = snackbar.view
        snackbarView.minimumHeight = 300
        snackbarView.setBackgroundColor(Color.BLACK)
        val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
        textView.maxLines =6;
        snackbar.show()
    }

    @Subscribe
    fun printFails(actionFailed: ActionFailed) {
        val snackbar = Snackbar
                .make(coordinatorLayout, actionFailed.message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setActionTextColor(Color.WHITE)
        val snackbarView = snackbar.view
        snackbarView.minimumHeight = 300
        snackbarView.setBackgroundColor(Color.BLACK)
        val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
        textView.maxLines =6;
        snackbar.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.

        val id = item.itemId
        when (id) {
            R.id.action_settings -> {
                val settingFragment = Intent(this, EditSettingsUI::class.java)
                startActivity(settingFragment)
            }
        }
        return super.onOptionsItemSelected(item)

    }


    override fun showDrawerToggle(showDrawerToggle: Boolean) {
        if (showDrawerToggle) {
            mDrawerToggle.isDrawerIndicatorEnabled = true
            if (supportActionBar != null)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            mDrawerToggle.syncState()
        } else {

            mDrawerToggle.isDrawerIndicatorEnabled = false
            if (supportActionBar != null)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeButtonEnabled(true)
            }
            mDrawerToggle.syncState()

        }


    }

    override fun setTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar?.title = title
        }
    }

    override fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentFound = fragmentManager.findFragmentByTag(fragment.javaClass.name)
        if (fragmentFound == null) {
            fragmentManager.beginTransaction().replace(R.id.containerView, fragment, fragment.javaClass.name).addToBackStack(fragment.javaClass.name).commit()
            fragmentManager.executePendingTransactions()
        } else {
            fragmentManager.beginTransaction().replace(R.id.containerView, fragmentFound, fragmentFound.javaClass.name).addToBackStack(fragmentFound.javaClass.name).commit()
            fragmentManager.executePendingTransactions()
        }

    }

    override fun addFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.containerView, fragment, fragment.javaClass.name).addToBackStack(fragment.javaClass.name).commit()
        fragmentManager.executePendingTransactions()


    }

    override fun getFragment(fragmentTagName: String): Fragment? {
        val fragmentManager = supportFragmentManager
        return fragmentManager.findFragmentByTag(fragmentTagName)

    }

    override fun showActivity(cls: Class<*>) {
        val intent = Intent(this@MainActivity, cls)
        startActivity(intent)
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}
