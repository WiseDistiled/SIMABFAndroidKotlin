import android.content.SharedPreferences
import com.tiramakan.simabf.BuildConfig
import com.tiramakan.simabf.bootstrap.util.MyEmulatorDetector
import com.tiramakan.simabf.bootstrap.util.MyPreferences
import com.tiramakan.simabf.core.constants.Constants
import android.R.id.edit



class MyPreferencesImpl(private val myEmulatorDetector: MyEmulatorDetector, internal var preferences: SharedPreferences) : MyPreferences {
    override val typePrix: String
        get() = preferences.getString("typePrix", "DETAILS")?:""

    internal var showPrefOnly: Boolean = false
    override val login: String
        get() = preferences.getString("login", "")?:""
    override val isCountryMali: Boolean
        get() = login.startsWith("+223") || login.startsWith("00223")
    override val isCountryBurkina: Boolean
        get() = login.startsWith("+226") || login.startsWith("00226")
    override val dbName: String
        get() = if (isCountryMali)
            "simabf.realm"
        else
            "simabf.realm"

    override val nomComplet: String
        get() = preferences.getString("nomComplet", "")?:""
    override var token: String
        get() = preferences.getString("token", "")?:""
        set(value) {
            val editor = preferences.edit()
            editor.putString("token", value)
            editor.apply()
        }

    override val password: String
        get() = preferences.getString("password", "")?:""

    override val webserviceURL: String
        get() {
            val forceURLProduction = preferences.getBoolean("forceURLProduction", false)
            var proposedUrl = preferences.getString("webServiceServer", Constants.Http.URL_DEFAULT)
            if (isCountryMali)
                proposedUrl = Constants.Http.URL_WEBSERVER_MALI
            if (forceURLProduction) {
                 if (isCountryMali)
                     proposedUrl =  Constants.Http.URL_WEBSERVER_MALI
                else
                     proposedUrl = Constants.Http.URL_DEFAULT
                return proposedUrl
            } else {
                if (BuildConfig.DEBUG) {
                    if (myEmulatorDetector.isSamsung || myEmulatorDetector.isInfinix || myEmulatorDetector.isTecno)
                        proposedUrl = Constants.Http.URL_BASE_LOCALHOST
                    else
                        proposedUrl = Constants.Http.URL_BASE
                }
                return proposedUrl!!
            }
        }

    override val adminMail: String
        get() {
            val proposedUrl = preferences.getString("adminMail", Constants.Auth.ADMIN_MAIL)
            return if (proposedUrl?.trim { it <= ' ' } != "")
                proposedUrl!!
            else
                Constants.Auth.ADMIN_MAIL
        }

    override val marche: String
        get() = preferences.getString("preferred_marche", "")?:""
    override val sendRequestBySMS: Boolean
        get() = preferences.getBoolean("sendRequestBySMS", true)
    override val forceTransportSMS: Boolean
        get() = preferences.getBoolean("forceTransportSMS", false)

    override val defaultCurrency: String
        get() = preferences.getString("defaultCurrency", "FCFA")?:""

    override val defaultWebPage: String
        get() = preferences.getString("simagriDashBoard", Constants.Http.DEFAULT_DASHBOARD_URL)?:""


    override val depot: String
        get() = preferences.getString("preferred_depot", "Magazin Sankariaré")?:""
    override var numeroCourt: String
        get() {
            var numeroCourt = preferences.getString("numeroCourt", Constants.Extra.NUMERO_COURT)

            if (isCountryBurkina)
                numeroCourt = Constants.Extra.NUMERO_COURT

            return numeroCourt?:""
        }
        set(value) = preferences.edit().putString("numeroCourt", value).apply()
    override val isLoginAndPasswordOk: Boolean
        get() = login != "" && password != ""

    override fun setPreferencesOnly(value: Boolean) {
        showPrefOnly = value
    }


    override fun toString(): String {
        return "Login : $login Password : $password Web Service URL : $webserviceURL"
    }

}
