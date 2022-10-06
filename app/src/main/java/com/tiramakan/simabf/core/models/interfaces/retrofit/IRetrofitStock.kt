package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.POST_STOCKS
import com.tiramakan.simabf.core.models.notifiers.RequestResponse

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

//import retrofit.Call;
//import retrofit.http.Multipart;
//import retrofit.http.POST;
//import retrofit.http.Part;
//import retrofit.http.Query;

/**
 * Utilisateur service for connecting the the REST API and
 * getting the utilisateurs.
 */
interface IRetrofitStock {
    @Multipart
    @POST(POST_STOCKS)
    fun send(@Part("depot") depot: RequestBody?, @Part("mesure") mesure: RequestBody?, @Part("produit") produit: RequestBody?, @Part("date") date: RequestBody, @Part("balance") balance: RequestBody): Call<RequestResponse>


    /**
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
