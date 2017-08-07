package com.deepshooter.simplepresenterapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.deepshooter.simplepresenterapp.R;
import com.deepshooter.simplepresenterapp.home.HomeActivity;
import com.deepshooter.simplepresenterapp.utils.Helper;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener ,LoginPresenterClass.LoginMethod {

    @InjectView(R.id.vE_emailEditText)
    EditText mEmailEditText;
    @InjectView(R.id.vE_passwordEditText)
    EditText mPasswordEditText;
    @InjectView(R.id.vT_sA_submit)
    TextView mSubmit;


    Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        setValues();
    }


    private void setValues()
    {
        mHelper = new Helper(getApplicationContext());

        try {
            mHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.vT_sA_submit:

                String mEmail = mEmailEditText.getText().toString().trim();
                String mPassword = mPasswordEditText.getText().toString().trim();

                new LoginPresenterClass(LoginActivity.this,LoginActivity.this,mEmail,mPassword);

                break;
        }

    }

    @Override
    public void loginClick(boolean IsSuccess) {

       if(IsSuccess)
       {
           Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
           startActivity(intent);
           finish();
       }

    }
}
