package com.deepshooter.simplepresenterapp.utils;

/**
 * Created by Avinash on 12-07-2017.
 */

public interface OnResponseListner<T> {

    void onResponse(T response, WebServices.ApiType URL, boolean isSucces);

}
