package com.deepshooter.simplepresenterapp.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.deepshooter.simplepresenterapp.R;
import com.deepshooter.simplepresenterapp.login.model.LoginOutput;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends AppCompatActivity implements HomePresenterClass.GetLoginDataMethod {


    @InjectView(R.id.vT_message)
    TextView mVTMessage;
    @InjectView(R.id.vT_email)
    TextView mVTEmail;

    ArrayList<LoginOutput> mLoginOutputArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        setValues();
    }


    private void setValues() {

        new HomePresenterClass(HomeActivity.this,HomeActivity.this);

        mVTMessage.setText(mLoginOutputArrayList.get(0).getMessage());
        mVTEmail.setText(mLoginOutputArrayList.get(0).getEmail());


    }


    @Override
    public void getLoginData(ArrayList<LoginOutput> outputArrayList) {

        mLoginOutputArrayList = outputArrayList;
    }


}
