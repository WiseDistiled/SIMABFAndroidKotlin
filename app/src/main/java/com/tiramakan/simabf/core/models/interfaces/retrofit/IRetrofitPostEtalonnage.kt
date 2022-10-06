package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.POST_ETALONNAGE
import com.tiramakan.simabf.core.models.notifiers.RequestResponse

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface IRetrofitPostEtalonnage {
    @Multipart
    @POST(POST_ETALONNAGE)
    fun send(@Part("uml") uml: RequestBody?, @Part("ul") ul: RequestBody?, @Part("valeur") valeur: RequestBody?,
             @Part("date") date: RequestBody,@Part("marche") marche: RequestBody): Call<RequestResponse>
    /**
     *
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
