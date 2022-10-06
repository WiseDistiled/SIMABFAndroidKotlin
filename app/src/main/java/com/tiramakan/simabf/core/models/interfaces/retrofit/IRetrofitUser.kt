package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.URL_REGISTER_USER
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
interface IRetrofitUser {
    @Multipart
    @POST(URL_REGISTER_USER)
    fun send(@Part("mobilePhone") phone: RequestBody, @Part("prenom") firstname: RequestBody, @Part("nom") name: RequestBody, @Part("genre") gender: RequestBody,
              @Part("email") email: RequestBody,
             @Part("secondPhone") secondPhone: RequestBody, @Part("reseau") reseau: RequestBody
    ): Call<RequestResponse>

}
