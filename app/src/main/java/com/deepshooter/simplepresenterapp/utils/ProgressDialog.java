package com.deepshooter.simplepresenterapp.utils;

import android.content.Context;

import com.deepshooter.simplepresenterapp.R;

/**
 * Created by Avinash on 12-07-2017.
 */

public class ProgressDialog {

    private static android.app.ProgressDialog pdLoading = null;

    public static android.app.ProgressDialog getInstance(Context context) {
        if (pdLoading == null || pdLoading.getContext() != context) {
            pdLoading = new android.app.ProgressDialog(context);
            pdLoading.setMessage(context.getResources().getString(R.string.please_wait));
            pdLoading.setCancelable(false);
            pdLoading.setCanceledOnTouchOutside(false);
            return pdLoading;
        }

        return pdLoading;
    }
}
