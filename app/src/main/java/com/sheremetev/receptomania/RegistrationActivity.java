package com.sheremetev.receptomania;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class RegistrationActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText passwordConfirm;
    CheckBox chkBox;

    ProgressBar progressBar;
    String Responce = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    public void Registration(View view) throws Exception {

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        passwordConfirm = (EditText) findViewById(R.id.passwordConfirm);
        chkBox = (CheckBox) findViewById(R.id.chkBox);


        if(email.getText().toString().equals("") || password.getText().toString().equals("") ||
                passwordConfirm.getText().toString().equals("")){
            Toast.makeText(RegistrationActivity.this,"Заполните все поля!",Toast.LENGTH_SHORT).show();
        }else if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
            Toast.makeText(RegistrationActivity.this, "Вводимые пароли совпадают", Toast.LENGTH_SHORT).show();
        }else if(!chkBox.isChecked()){
            Toast.makeText(RegistrationActivity.this,"Примите условия пользовательского соглашения",Toast.LENGTH_SHORT).show();
        } else {


            final Activity activity = RegistrationActivity.this;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        JSONObject postDataParams = new JSONObject();
                        postDataParams.put("email", email.getText().toString());
                        postDataParams.put("password", password.getText().toString());

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                                email.setText("");
                                password.setText("");
                                passwordConfirm.setText("");
                            }
                        });

                        String url = "https://texturrariaone.ru/sher/register.php";
                        String postDataParamsString = new PostDataStringer().getPostDataString(postDataParams);

                        SendPostData sendPostData = new SendPostData();

                        sendPostData.execute(url, postDataParamsString);

                        Responce = sendPostData.get();

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity,Responce,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });

                    }
                    catch (Exception e){
                        Toast.makeText(RegistrationActivity.this,"Ошибка",Toast.LENGTH_LONG).show();
                    }
                }
            }).start();
        }
    }
}