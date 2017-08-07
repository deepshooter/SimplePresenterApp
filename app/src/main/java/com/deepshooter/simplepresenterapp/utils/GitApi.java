package com.deepshooter.simplepresenterapp.utils;

import com.deepshooter.simplepresenterapp.login.model.LoginInput;
import com.deepshooter.simplepresenterapp.login.model.LoginOutput;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Avinash on 12-07-2017.
 */

public interface GitApi {


    @POST("login")
    Call<LoginOutput> getLoginCall(@Body LoginInput loginInput);

}
