package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.URL_PRODUCTS_FRAG
import com.tiramakan.simabf.core.models.realm.Produit

import retrofit2.Call
import retrofit2.http.GET

/**
 * Utilisateur service for connecting the the REST API and
 * getting the utilisateurs.
 */
interface IretrofitProduit {

    @get:GET(URL_PRODUCTS_FRAG)
    val produits: Call<List<Produit>>

    /**
     * The [Query] values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The utilisateurs email
     * @param password The utilisateurs password
     * @return A login response.
     */
}
