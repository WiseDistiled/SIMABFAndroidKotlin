package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.POST_GPS_MARCHE
import com.tiramakan.simabf.core.models.notifiers.RequestResponse

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Utilisateur service for connecting the the REST API and
 * getting the utilisateurs.
 */
interface IRetrofitGPSMarche {
    @Multipart
    @POST(POST_GPS_MARCHE)
    fun send(@Part("date") date: RequestBody, @Part("longitude") longitude: RequestBody, @Part("latitude") latitude: RequestBody, @Part("marche") marche: RequestBody): Call<RequestResponse>


    /**
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
