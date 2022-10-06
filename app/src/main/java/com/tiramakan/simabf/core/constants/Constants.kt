package com.tiramakan.simabf.core.constants

/**
 * Bootstrap constants
 */
object Constants {

    object Auth {

        /**
         * ADMIN URL
         */
        val ADMIN_MAIL = "tiramakantraore@gmail.com"

    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for bootstrap!
     */
    object Http {

        /**
         * Default URL for all requests
         */
        val URL_DEFAULT = "https://sima-bf.net:8877/"

        val URL_WEBSERVER_MALI = "https://sima-bf.net:8877/"
        /**
         * Base URL for all requests
         */
        val URL_BASE = "http://10.0.2.2:8091/"
        /**
         * Base URL for all requests
         */
        val URL_BASE_LOCALHOST = "http://192.168.0.106:8091/"

        /**
         * Default Dashboard URL
         */
        val DEFAULT_DASHBOARD_URL = "https://www.sima-bf.net"


        /**
         * Register Users URL
         */
        const val URL_REGISTER_USER = "/api/acteur/enregistrerAndroid"



        /**
         * List Produits URL
         */
        const val URL_PRODUCTS_FRAG = "api/produits.json"

        /**
         * List Qualities URL
         */
        const val URL_QUALITY_FRAG = "api/qualites.json"

        /**
         * List Qualities URL
         */
        const val URL_ETATAPPRO_FRAG = "api/etatAppro.json"


        /**
         * List Produits URL
         */
        const val URL_MEASURES_FRAG = "api/mesures.json"

        /**
         * List Produits URL
         */
        const val URL_TYPE_PRIX_FRAG = "api/typePrix.json"


        /**
         * List Users URL
         */
        const val URL_USERPARAMS_FRAG = "api/userparams.json"


        /**
         * save prices URL URL
         */
        const val POST_PRICES = "/api/prix/saveFromAndroid"

        /**
         * get prices URL URL
         */
        const val GET_DATA = "api/getdata"


        /**
         * save offers URL URL
         */

        const val POST_OFFERS_VENTE = "/api/offre_vente/enregistrerAndroid"
        const val POST_OFFERS_ACHAT = "/api/offre_achat/enregistrerAndroid"
      //  const val POST_OFFERS = "api/offers/save"

        /**
         * save infos URL URL
         */
        const val POST_NEWS = "api/news/save"

        /**
         * save notes URL URL
         */
        const val POST_NOTES = "/api/noteMarche/enregistrerAndroid"

        /**
         * save GPS Position URL URL
         */
        const val POST_GPS_DEPOT = "api/depot/enregistrerGPS"

        /**
         * save GPS Position URL URL
         */
        const val POST_GPS_MARCHE = "api/marche/enregistrerGPS"

        /**
         * save prices URL URL
         */
        const val POST_STOCKS = "/api/stock/enregistrerAndroid"

        /**
         * save prices URL URL
         */
        const val POST_ETALONNAGE = "/api/etalonnage/enregistrerAndroid"

        /**
         * save prices URL URL
         */

        const val URL_LOGIN = "api/login"

        /**
         * List Marches URL
         */
        const val URL_MARKETS_FRAG = "api/lesmarches.json"
        /**
         * List Marches URL
         */
        const val URL_ETALONNAGES_FRAG = "api/etalonnages.json"
        /**
         * List Marches URL
         */
        const val URL_MARCHE_ACTEUR_FRAG = "api/marchesacteurs.json"


        const val URL_DEPOT_FRAG = "api/depots.json"

        const val URL_RESEAU_FRAG = "api/reseaux.json"

        const val URL_REGION_FRAG = "api/regions.json"
    }


    object Extra {

        val REQUEST_RESPONSE_TAG = "REQUEST_RESPONSE_TAG"
        val NUMERO_COURT = "3459"

        val TYPE_PRIX_TAG = "TYPE_PRIX_TAG"
        val TYPE_OFFRE_TAG = "TYPE_OFFRE_TAG"
        val NOM_MARCHE_TAG = "NOM_MARCHE_TAG"
        val NOM_PRODUIT_TAG = "NOM_PRODUIT_TAG"
        val NOM_DEPOT_TAG = "NOM_DEPOT_TAG"
        val NOM_MESURE_TAG = "NOM_MESURE_TAG"
        val LAST_SAVED_PRICE_TAG = "LAST_SAVED_PRICE_TAG"
        val LAST_SAVED_STOCK_TAG = "LAST_SAVED_STOCK_TAG"
        val LAST_SAVED_OFFRE_ACHAT_TAG = "LAST_SAVED_OFFRE_ACHAT_TAG"
        val LAST_SAVED_OFFRE_VENTE_TAG = "LAST_SAVED_OFFRE_VENTE_TAG"
        val STOCK_TAG = "STOCK_TAG"
        val OFFER_TO_UI_BUNDLE = "OFFER_TO_UI_BUNDLE"
        val USER_TO_UI_BUNDLE = "USERS_TO_UI_BUNDLE"
        val USER_TO_UI_TAG = "USERS_TO_UI_TAG"
        val PRICE_TAG = "PRICE_TAG"
        val MAX_IMAGE_SIZE = 6 * 1024 * 1024//6 mo


    }

    object Intent

    object Notification

    class ClasseName
    object Location {
        val LOCATION_REFRESH_TIME: Long = 1000
        val LOCATION_REFRESH_DISTANCE = 0f
    }


}


