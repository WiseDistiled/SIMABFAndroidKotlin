package com.tiramakan.simabf.ui.view.baseClasses

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.BootstrapServiceProvider
import com.tiramakan.simabf.bootstrap.beansProviders.RealmServiceProvider
import com.tiramakan.simabf.bootstrap.util.MyPreferences
import com.tiramakan.simabf.ui.view.interfaces.MyActivityInterface
import com.tiramakan.simabf.ui.view.interfaces.OnBackPressedListener
import com.tiramakan.simabf.ui.view.interfaces.OnFragmentInteractionListener

import javax.inject.Inject

import io.realm.Realm

/**
 * Created by Ratan on 7/29/2015.
 */
open class BaseFragment : Fragment(), OnBackPressedListener {

     lateinit var bus: Bus
    @Inject set
     lateinit var realmServiceProvider: RealmServiceProvider
    @Inject set
     lateinit var serviceProvider: BootstrapServiceProvider
         @Inject set
     open lateinit var mListener: OnFragmentInteractionListener
     lateinit var myParent: MyActivityInterface
    lateinit var myPreferences: MyPreferences
         @Inject set
     lateinit var inputMethodManager: InputMethodManager
    var isSended: Boolean = false
    lateinit var doBackListener: OnBackPressedListener
    protected open val realm: Realm
        get() = realmServiceProvider.realm


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mListener = context as OnFragmentInteractionListener
        this.myParent = (context as MyActivityInterface)
        this.doBackListener = this
        this.isSended = false

    }

    protected fun showFragment(fragment: Fragment) {
        myParent.showFragment(fragment)
    }

       protected fun addFragment(fragment: Fragment) {
        myParent.addFragment(fragment)
    }


    fun setOnBackPressedListener(l: OnBackPressedListener) {
        doBackListener = l
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("sended", this.isSended)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isSended = savedInstanceState?.getBoolean("sended", false)?:false

    }
    override fun onStart() {
        super.onStart()
        bus.register(this)
    }

    override fun onStop() {
        super.onStop()
        bus.unregister(this)
    }
        override fun onResume() {
        super.onResume()


            mListener.showDrawerToggle(false)

        }

    override fun onPause() {
        //   this.doBackListener=null;
        super.onPause()

    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

    override fun doBack(): Boolean {
        return false
    }
}
