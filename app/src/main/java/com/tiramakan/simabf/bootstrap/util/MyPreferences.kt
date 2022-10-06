package com.tiramakan.simabf.bootstrap.util

import android.content.SharedPreferences

import com.tiramakan.simabf.BuildConfig
import com.tiramakan.simabf.core.constants.Constants

/**
 * Created by tiramakan on 17/01/2016.
 */
interface MyPreferences {
    val login: String
    val password: String
    val webserviceURL: String
    val marche: String
    val depot: String
    val typePrix: String
    val defaultCurrency: String
    val defaultWebPage: String
    var numeroCourt: String
    val nomComplet: String
    val sendRequestBySMS: Boolean
    val forceTransportSMS: Boolean
    val isLoginAndPasswordOk: Boolean
    val isCountryMali: Boolean
    val dbName: String
    val adminMail: String
    var token: String
    val isCountryBurkina: Boolean
    fun setPreferencesOnly(value: Boolean)

}

