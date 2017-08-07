package com.deepshooter.simplepresenterapp.utils;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.util.Log;

import com.deepshooter.simplepresenterapp.login.model.LoginInput;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Avinash on 12-07-2017.
 */

public class WebServices<T> {


    private T t;
    Call<T> call = null;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    ApiType apiTypeVariable;
    Context context;
    OnResponseListner<T> onResponseListner;

    android.app.ProgressDialog pdLoading;
    private static OkHttpClient.Builder builder;

    public final static String PublicDevService=" https://www.abcd.in/abcdservices/api/";
    public final static String PublicService = PublicDevService;


    public enum ApiType { postLogin }




    public WebServices(OnResponseListner<T> onResponseListner , Context context) {
        if (onResponseListner instanceof Activity) {
            this.context = (Context) onResponseListner;
        } else if (onResponseListner instanceof IntentService) {
            this.context = (Context) onResponseListner;
        } else if (onResponseListner instanceof android.app.DialogFragment) {
            android.app.DialogFragment dialogFragment = (android.app.DialogFragment) onResponseListner;
            this.context = dialogFragment.getActivity();
        } else if(onResponseListner instanceof android.support.v4.app.Fragment) {
            android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) onResponseListner;
            this.context = fragment.getActivity();

        } /*else if (onResponseListner instanceof Object) {
            this.context = context;

        }*/else{
           /* Fragment fragment = (Fragment) onResponseListner;
            this.context = fragment.getActivity();*/

            this.context = context;
        }
        this.onResponseListner= onResponseListner;

        builder = getHttpClient();
    }



    public OkHttpClient.Builder getHttpClient() {
        if (builder == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(loggingInterceptor);
            client.writeTimeout(60000, TimeUnit.MILLISECONDS);
            client.readTimeout(60000,TimeUnit.MILLISECONDS);
            client.connectTimeout(60000,TimeUnit.MILLISECONDS);
            return client;
        }
        return builder;
    }




    public void postLogin(String api, ApiType apiTypes, LoginInput loginInput) {
        apiTypeVariable = apiTypes;

        try {
            pdLoading = ProgressDialog.getInstance(context);
            pdLoading.show();
        } catch (Exception e) {
            // e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
        GitApi gi = retrofit.create(GitApi.class);
        call = (Call<T>) gi.getLoginCall(loginInput);
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                t = (T) response.body();
                if (pdLoading.isShowing())
                    pdLoading.cancel();
                onResponseListner.onResponse(t, apiTypeVariable, true);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.d(apiTypeVariable.toString(), t.getMessage() + ".");
                if (pdLoading.isShowing())
                    pdLoading.cancel();
                onResponseListner.onResponse(null, apiTypeVariable, false);
            }

        });
    }




}
