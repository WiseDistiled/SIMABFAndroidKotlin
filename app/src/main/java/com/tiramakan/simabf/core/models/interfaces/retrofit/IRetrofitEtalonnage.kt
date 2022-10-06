package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.URL_ETALONNAGES_FRAG
import com.tiramakan.simabf.core.constants.Constants.Http.URL_MARKETS_FRAG
import com.tiramakan.simabf.core.models.realm.Etalonnage
import com.tiramakan.simabf.core.models.realm.Marche

import retrofit2.Call
import retrofit2.http.GET

/**
 * Utilisateur service for connecting the the REST API and
 * getting the utilisateurs.
 */
interface IRetrofitEtalonnage {

    @get:GET(URL_ETALONNAGES_FRAG)
    val etalonnages: Call<List<Etalonnage>>

    /**
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
