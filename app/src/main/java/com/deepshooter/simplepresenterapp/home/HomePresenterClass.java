package com.deepshooter.simplepresenterapp.home;

import android.content.Context;

import com.deepshooter.simplepresenterapp.login.model.LoginOutput;
import com.deepshooter.simplepresenterapp.utils.Helper;

import java.util.ArrayList;

/**
 * Created by Admin on 13-07-2017.
 */

public class HomePresenterClass {

    private Helper mHelper ;
    private Context context;
    private ArrayList<LoginOutput> mLoginOutputArrayList;
    private GetLoginDataMethod mGetLoginDataMethod;


    public HomePresenterClass(Context context, GetLoginDataMethod getLoginDataMethod) {
        this.context = context;
        mGetLoginDataMethod = getLoginDataMethod;

        callLoginDatabase();
    }

    private void callLoginDatabase()
    {

        mHelper = new Helper(context);
        mHelper.openDataBase();
        mLoginOutputArrayList = mHelper.getAllLoginData();
        mHelper.close();
        mGetLoginDataMethod.getLoginData(mLoginOutputArrayList);
    }

    interface GetLoginDataMethod{

        void getLoginData(ArrayList<LoginOutput> outputArrayList);
    }

}
