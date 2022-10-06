package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.URL_REGION_FRAG
import com.tiramakan.simabf.core.constants.Constants.Http.URL_RESEAU_FRAG
import com.tiramakan.simabf.core.models.realm.Region

import retrofit2.Call
import retrofit2.http.GET

/**
 * Utilisateur service for connecting the the REST API and
 * getting the utilisateurs.
 */
interface IRetrofitRegion {

    @get:GET(URL_REGION_FRAG)
    val regions: Call<List<Region>>

    /**
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
