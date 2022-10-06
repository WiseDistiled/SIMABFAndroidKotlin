package com.tiramakan.simabf.ui.view

/**
 * Created by tiramakan on 17/01/2016.
 */
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment

/**
 * Created by tiramakan on 17/01/2016.
 */
class AuthenticateFragment : BaseFragment() {
    internal lateinit var login: EditText
    internal lateinit var password: EditText
    internal lateinit var validerBtn: AppCompatButton
    //Wire the layout to the step
    override fun onResume() {
        super.onResume()
        this.doBackListener = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(false)
    }

    override fun doBack(): Boolean {
        return true
    }

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.authenticate_layout, container, false)
        login = view.findViewById<View>(R.id.login) as EditText
        password = view.findViewById<View>(R.id.password) as EditText
        login.setText(myPreferences.login)
        password.setText(myPreferences.password)

        validerBtn = view.findViewById<View>(R.id.validerBtn) as AppCompatButton
        validerBtn.setOnClickListener { validate() }
        return view

    }

    override fun onDetach() {
        super.onDetach()
    }


    private fun validate() {
        if (login.text.toString() != "" && password.text.toString() != "") {
            if (context?.let { UIUtils.isValidPhoneNumber(it, login.text.toString()) }!!) {
                val settings = PreferenceManager.getDefaultSharedPreferences(context)
                val editor = settings.edit()
                editor.putString("login", login.text.toString())
                editor.putString("password", password.text.toString())
                editor.apply()
                editor.putString("numeroCourt", Constants.Extra.NUMERO_COURT)

                editor.apply()
                if (this.serviceProvider.service.login()) {
                    showFragment(Welcome())
                    } else
                    bus.post("Echec de l'authentification verifiez votre code utilisateur et votre mot de passe ou contactez votre administrateur ")
            }
        } else
            bus.post("Vous devez renseigner un login et un mot de passe")

    }


}
