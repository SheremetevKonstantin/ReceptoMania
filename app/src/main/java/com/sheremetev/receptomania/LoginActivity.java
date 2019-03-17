package com.sheremetev.receptomania;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    private SQLiteDatabase db;
    private Cursor cursor;
    String isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        try {
            UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(this);
            db = userDatabaseHelper.getReadableDatabase();
            int vers =  db.getVersion();

            cursor = db.query("USER",
                    new String[] {"_id","USER_NAME","ISLOGIN"},
                    null,null,null,null,null);
            if(cursor.moveToFirst()){
                String userName = cursor.getString(1);
                isLogin = cursor.getString(2);
                if(isLogin.equals("1")){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra(MainActivity.EXTRA_EMAIL_MESSAGE,userName);
                    startActivity(intent);
                }
            }


        }catch (SQLException e){
            Toast toast = Toast.makeText(this,"База данных недоступна",Toast.LENGTH_SHORT);
            toast.show();
        }
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

                                    updateMyDatabase();

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

    public void updateMyDatabase(){
        ContentValues userValues = new ContentValues();
        userValues.put("USER_NAME", emailText);
        userValues.put("ISLOGIN", "1");
        UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(this);
        try{
            SQLiteDatabase db = userDatabaseHelper.getWritableDatabase();
            db.update("USER",
                    userValues,
                    "_id = ?",
                    new String[]{Integer.toString(0)});
            db.close();


        }catch (SQLException e){
            Toast toast = Toast.makeText(this,"База данных недоступна",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}