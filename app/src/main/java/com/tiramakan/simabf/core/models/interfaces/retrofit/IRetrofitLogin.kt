package com.tiramakan.simabf.core.models.interfaces.retrofit

import com.tiramakan.simabf.core.constants.Constants.Http.URL_LOGIN
import com.tiramakan.simabf.core.models.retrofitObjets.Login
import com.tiramakan.simabf.core.models.retrofitObjets.UserRetrieved

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface IRetrofitLogin {
    @POST(URL_LOGIN)
    fun login(@Body login: Login): Call<UserRetrieved>

}
