package com.example.persistenthotel.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.persistenthotel.R;
import com.example.persistenthotel.model.Guest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN_KEY ="get.login.info";
    public static final int RETURN_KEY =1;

    @BindView(R.id.signinUsername)
    EditText signinUsernameView;
    @BindView(R.id.passwordVIew)
    EditText passwordView;

    private Guest loginGuest;
    private boolean loginSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();

        loginGuest = (Guest) data.getParcelable(LOGIN_KEY);

    }

    public void signinToGuest(View view){
        String username = signinUsernameView.getText().toString();
        String password = passwordView.getText().toString();

        //Log.d("TAG_H", "Username:  " + username);
        //Log.d("TAG_H", "Password:  " + password);
        //Log.d("TAG_H", "Username2:  " + loginGuest.getActualName());
        //Log.d("TAG_H", "Password2:  " + loginGuest.getPassword());


        if(username.equals(loginGuest.getActualName()) && password.equals(loginGuest.getPassword())){
            loginSuccess =true;
        }
        else
            loginSuccess=false;

        Intent resultIntent = new Intent();
        resultIntent.putExtra("Boolean", loginSuccess);
        resultIntent.putExtra("Guest",loginGuest);
        setResult(RETURN_KEY, resultIntent);
        finish();
    }


}
