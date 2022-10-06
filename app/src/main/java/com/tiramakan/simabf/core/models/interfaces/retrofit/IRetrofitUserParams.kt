package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.URL_USERPARAMS_FRAG
import com.tiramakan.simabf.core.models.realm.Mesure
import com.tiramakan.simabf.core.models.realm.UserParam

import retrofit2.Call
import retrofit2.http.GET

/**
 * Utilisateur service for connecting the the REST API and
 * getting the utilisateurs.
 */
interface IRetrofitUserParams {

    @get:GET(URL_USERPARAMS_FRAG)
    val usersParams: Call<List<UserParam>>

    /**
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
