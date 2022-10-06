package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.URL_QUALITY_FRAG
import com.tiramakan.simabf.core.models.realm.Qualite

import retrofit2.Call
import retrofit2.http.GET

/**
 * Utilisateur service for connecting the the REST API and
 * getting the utilisateurs.
 */
interface IRetrofitQualite {

    @get:GET(URL_QUALITY_FRAG)
    val qualities: Call<List<Qualite>>

    /**
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
