package com.tiramakan.simabf.bootstrap


import com.tiramakan.simabf.bootstrap.util.MyPreferences

/**
 * Created by tiramakan on 01/01/2016.
 */
class APIAuthenticatorImpl(internal var preferences: MyPreferences) : APIAuthenticator {
    override val login: String
        get() = preferences.login.toString()
    override val password: String
        get() = preferences.password.toString()
}
