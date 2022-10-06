package com.tiramakan.simabf.ui.view.edits

/**
 * Created by tiramakan on 17/01/2016.
 */

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.beansProviders.ReseauProvider
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.constants.Constants.Extra.USER_TO_UI_TAG
import com.tiramakan.simabf.core.modelView.UserToUI
import com.tiramakan.simabf.core.models.savers.ISaverUser
import com.tiramakan.simabf.databinding.FrameRegisterUserBinding
import com.tiramakan.simabf.ui.view.Lists.ListUsersUI
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.utils.Binding.BindingComponent

import org.parceler.Parcels

import java.util.ArrayList

import javax.inject.Inject

/**
 * Created by tiramakan on 17/01/2016.
 */
//Wire the layout to the step
class EditUserUI : BaseFragment() {
    var iSaverUser: ISaverUser? = null
        @Inject set
    var userToUI: UserToUI? = null
    var reseauProvider: ReseauProvider? = null
        @Inject set
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)
        if (savedInstanceState == null) {
            if (userToUI == null)
                userToUI = UserToUI()
        } else {
            userToUI = Parcels.unwrap(savedInstanceState.getParcelable(USER_TO_UI_TAG))

        }
        if (arguments != null) {
            val userToUIBundle = arguments?.getBundle(Constants.Extra.USER_TO_UI_BUNDLE)


            if (userToUIBundle != null)
                userToUI = Parcels.unwrap(userToUIBundle.getParcelable(Constants.Extra.USER_TO_UI_TAG))

        }
    }

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        DataBindingUtil.setDefaultComponent(BindingComponent())
         if (savedInstanceState == null) {
            if (userToUI == null)
                userToUI = UserToUI()
        } else {
            userToUI = Parcels.unwrap(savedInstanceState.getParcelable(USER_TO_UI))
            if (userToUI == null)
                userToUI = UserToUI()
        }
        userToUI?.reseauProvider = this.reseauProvider!!
        val binding = DataBindingUtil.inflate<FrameRegisterUserBinding>(inflater, R.layout.frame_register_user, container, false)

        binding.utilisateur = userToUI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            binding.reseau.background = context?.let { ContextCompat.getDrawable(it, R.drawable.abc_edit_text_material) }
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(USER_TO_UI, Parcels.wrap(userToUI))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when (id) {
            R.id.action_next -> if (userToUI?.validate()?:false) {
                userToUI?.let { iSaverUser?.save(it) }
                myParent.showFragment(ListUsersUI())
            } else {
            bus.post("Veuillez vérifier que vous avez saisi toutes les informations obligatoires")
        }
        }
        return true
    }

    override fun doBack(): Boolean {

        return true

    }

    companion object {
        val USER_TO_UI = "USER_TO_UI"
    }

}
