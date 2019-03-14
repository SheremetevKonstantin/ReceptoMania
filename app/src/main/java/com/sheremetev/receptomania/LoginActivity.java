package com.sheremetev.receptomania;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    String emailText;

    ProgressBar progressBar;
    String Responce = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }


    public void openRegActivity(View view){
        Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
        startActivity(intent);
    }

    public void Login(View view){

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        emailText = email.getText().toString();

        if(email.getText().toString().equals("") || password.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this,"Заполните все поля",Toast.LENGTH_SHORT).show();
        }else{

            final Activity activity = LoginActivity.this;

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
                            }
                        });

                        String url = "https://texturrariaone.ru/sher/login.php";
                        String postDataParamsString = new PostDataStringer().getPostDataString(postDataParams);

                        SendPostData sendPostData = new SendPostData();

                        sendPostData.execute(url, postDataParamsString);

                        Responce = sendPostData.get();

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                if(Responce.equals("!success")){
                                    Toast.makeText(activity,Responce,Toast.LENGTH_LONG).show();
                                }else{
                                    Intent intent = new Intent(activity,MainActivity.class);
                                    intent.putExtra(MainActivity.EXTRA_EMAIL_MESSAGE,emailText);
                                    startActivity(intent);
                                }
                            }
                        });

                    }
                    catch (Exception e){
                        Toast.makeText(LoginActivity.this,"Ошибка",Toast.LENGTH_LONG).show();
                    }
                }
            }).start();
        }
    }

    public void LoginAsGuest(View view){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_EMAIL_MESSAGE,"Гость");
        startActivity(intent);
    }
}