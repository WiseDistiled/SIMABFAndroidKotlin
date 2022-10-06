package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.constants.Constants.Http.GET_DATA
import com.tiramakan.simabf.core.models.notifiers.RequestResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//import retrofit.Call;
//import retrofit.http.GET;
//import retrofit.http.Query;

/**
 * Utilisateur service for connecting the the REST API and
 * getting the utilisateurs.
 */
interface IRetrofitRequest {
    @GET(GET_DATA)
    fun send(@Query("request") request: String
    ): Call<RequestResponse>


    /**
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
