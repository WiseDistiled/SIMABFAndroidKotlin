package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.POST_OFFERS_VENTE
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
interface IRetrofitOfferVente {
    @Multipart
    @POST(POST_OFFERS_VENTE)
    fun send(@Part("offerType") offerType: RequestBody, @Part("date") date: RequestBody, @Part("quantite") quantite: RequestBody,
             @Part("mesure") mesure: RequestBody, @Part("conditions") conditions: RequestBody,
             @Part("produit") produit: RequestBody?,
             @Part("prixUnitaire") unitPrice: RequestBody,
             @Part("montant") totalPrice: RequestBody,
             @Part("qualite") quality: RequestBody,
             @Part("nomAuteur") authorPhone: RequestBody,
             @Part("telephone") telephone: RequestBody,
             @Part("email") storagePlace: RequestBody,
             @Part("longitude") longitude: RequestBody,
             @Part("latitude") latitude: RequestBody,
             @Part("lieuxLivraison") lieuxLivraison: RequestBody,
             @Part("lieuxStockage") lieuxStockage: RequestBody,
             @Part("region") region: RequestBody
    ): Call<RequestResponse>
    @Multipart
    @POST(POST_OFFERS_VENTE)
    fun sendWithPhoto(@Part("offerType") offerType: RequestBody, @Part("date") date: RequestBody, @Part("quantite") quantite: RequestBody,
             @Part("mesure") mesure: RequestBody, @Part("conditions") conditions: RequestBody,
             @Part("produit") produit: RequestBody?,
             @Part("prixUnitaire") unitPrice: RequestBody,
             @Part("montant") totalPrice: RequestBody,
             @Part("qualite") quality: RequestBody,
             @Part("nomAuteur") authorPhone: RequestBody,
             @Part("telephone") telephone: RequestBody,
             @Part("email") storagePlace: RequestBody,
             @Part("longitude") longitude: RequestBody,
             @Part("latitude") latitude: RequestBody,
             @Part("photo") photo: RequestBody,
             @Part("lieuxLivraison") lieuxLivraison: RequestBody,
             @Part("lieuxStockage") lieuxStockage: RequestBody,
             @Part("region") region: RequestBody
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
