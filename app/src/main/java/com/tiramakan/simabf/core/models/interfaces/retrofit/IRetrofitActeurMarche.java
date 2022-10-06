package com.tiramakan.simabf.core.models.interfaces.retrofit;

import com.tiramakan.simabf.core.constants.Constants;
import com.tiramakan.simabf.core.models.realm.ActeurMarche;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * User service for connecting the the REST API and
 * getting the users.
 */
public interface IRetrofitActeurMarche {

    @GET(Constants.Http.URL_MARCHE_ACTEUR_FRAG)
    Call<List<ActeurMarche>> getMarcheOfActeurs();

    /**
     * The {@link Query} values will be transform into query string paramters
     * via Retrofit
     *
     * @param email The users email
     * @param password The users password
     * @return A login response.
     */
}
