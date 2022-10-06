package com.tiramakan.simabf.core.models.interfaces.retrofit
import com.tiramakan.simabf.core.constants.Constants.Http.POST_PRICES
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

interface IRetrofitPrice {
    @Multipart
    @POST(POST_PRICES)
    fun sendWithoutNote(@Part("typePrix") typePrix: RequestBody,@Part("marche") marche: RequestBody?, @Part("produit") produit: RequestBody?, @Part("date") date: RequestBody, @Part("montant") montant: RequestBody, @Part("mesure") mesure: RequestBody?, @Part("comment") comment: RequestBody): Call<RequestResponse>
    @Multipart
    @POST(POST_PRICES)
    fun sendWithNoteAndPhoto(@Part("photo") file: RequestBody, @Part("marche") typePrix: RequestBody, @Part("typePrix") marche: RequestBody?, @Part("produit") produit: RequestBody?, @Part("date") date: RequestBody, @Part("montant") montant: RequestBody?, @Part("mesure") mesure: RequestBody?
                             , @Part("note_contenu") note_contenu: RequestBody?, @Part("note_longitude") note_longitude: RequestBody?, @Part("note_latitude") note_latitude: RequestBody?, @Part("comment") comment: RequestBody): Call<RequestResponse>
    @Multipart
    @POST(POST_PRICES)
    fun sendWithNote(@Part("typePrix") typePrix: RequestBody,@Part("marche") marche: RequestBody?, @Part("produit") produit: RequestBody?, @Part("date") date: RequestBody, @Part("montant") montant: RequestBody, @Part("mesure") mesure: RequestBody?
    , @Part("note_contenu") note_contenu: RequestBody? , @Part("note_longitude") note_longitude: RequestBody?, @Part("note_latitude") note_latitude: RequestBody?, @Part("comment") comment: RequestBody): Call<RequestResponse>


    /**
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
