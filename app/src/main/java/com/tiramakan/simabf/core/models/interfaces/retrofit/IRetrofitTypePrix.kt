package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.URL_TYPE_PRIX_FRAG
import com.tiramakan.simabf.core.models.realm.TypePrix

import retrofit2.Call
import retrofit2.http.GET

/**
 * Utilisateur service for connecting the the REST API and
 * getting the utilisateurs.
 */
interface IRetrofitTypePrix {

    @get:GET(URL_TYPE_PRIX_FRAG)
    val typesPrix: Call<List<TypePrix>>

    /**
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
