package com.deepshooter.simplepresenterapp.login;

import android.content.Context;
import android.widget.Toast;

import com.deepshooter.simplepresenterapp.R;
import com.deepshooter.simplepresenterapp.customs.ConnectionDetector;
import com.deepshooter.simplepresenterapp.login.model.LoginInput;
import com.deepshooter.simplepresenterapp.login.model.LoginOutput;
import com.deepshooter.simplepresenterapp.utils.Helper;
import com.deepshooter.simplepresenterapp.utils.OnResponseListner;
import com.deepshooter.simplepresenterapp.utils.WebServices;

import java.util.regex.Pattern;

/**
 * Created by Admin on 12-07-2017.
 */

public class LoginPresenterClass implements OnResponseListner {

    Context context;
    LoginMethod mLoginMethod;
    String mEmail="";
    String mPassword="";
    Helper mHelper ;


    public LoginPresenterClass(Context context, LoginMethod loginMethod, String email, String password) {
        this.context = context;
        mLoginMethod = loginMethod;
        mEmail = email;
        mPassword = password;

        mHelper = new Helper(context);
        checkLogin(email,password);
    }


    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces) {

        if(URL== WebServices.ApiType.postLogin){

            if (isSucces){


                LoginOutput loginOutput = (LoginOutput) response;
                if(loginOutput != null)
                {

                    if(loginOutput.getSuccess())
                    {

                        LoginOutput loginOutputBean = new LoginOutput();
                        loginOutputBean.setEmail(loginOutput.getEmail());
                        loginOutputBean.setPassword(loginOutput.getPassword());
                        loginOutputBean.setSuccess(loginOutput.getSuccess());
                        loginOutputBean.setMessage(loginOutput.getMessage());
                        loginOutputBean.setOrganisationId(loginOutput.getOrganisationId());

                        mHelper.openDataBase();
                        mHelper.insertValueToLoginTable(loginOutputBean);
                        mHelper.close();

                        mLoginMethod.loginClick(true);

                    }else {

                        Toast.makeText(context.getApplicationContext(), loginOutput.getMessage()+"", Toast.LENGTH_LONG).show();
                        mLoginMethod.loginClick(false);
                    }


                }else {
                    mLoginMethod.loginClick(false);
                    Toast.makeText(context.getApplicationContext(), loginOutput.getMessage()+"", Toast.LENGTH_LONG).show();
                }


            }else {
                mLoginMethod.loginClick(false);
                Toast.makeText(context.getApplicationContext(), context.getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            }

        } else {
            mLoginMethod.loginClick(false);
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();

        }
    }



    private void checkLogin(String mEmail ,String mPassword )
    {

        if(mEmail.isEmpty() || mEmail.length()==0)
        {
            Toast.makeText(context.getApplicationContext(), "Please Enter Your Email Id", Toast.LENGTH_LONG).show();
        }else if(!checkEmail(mEmail))
        {
            Toast.makeText(context.getApplicationContext(), "Please Enter Correct Email Id", Toast.LENGTH_LONG).show();

        }else if(mPassword.isEmpty() || mPassword.length()==0)
        {
            Toast.makeText(context.getApplicationContext(), "Please Enter The Password", Toast.LENGTH_LONG).show();
        }else {

            callLoginService(mEmail ,mPassword);

        }



    }


    private void callLoginService(String mEmail , String mPassword)
    {
        ConnectionDetector mDetector = new ConnectionDetector(context.getApplicationContext());
        if (mDetector.isConnectingToInternet()) {

            WebServices<LoginOutput> loginOutputWebServices=new WebServices<>(LoginPresenterClass.this ,context);
            loginOutputWebServices.postLogin(WebServices.PublicService, WebServices.ApiType.postLogin,new LoginInput(mEmail,mPassword));


        } else {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }
    }



    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );



    interface LoginMethod{
        void loginClick(boolean IsSuccess);
    }
}
