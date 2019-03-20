package com.sheremetev.receptomania;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public final static String EXTRA_EMAIL_MESSAGE = "email";
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = (String)getIntent().getExtras().get(EXTRA_EMAIL_MESSAGE);
        if(email != null){
            if(email.equals("Гость")){
                setContentView(R.layout.activity_main_guest);
            }else{
                setContentView(R.layout.activity_main);
            }
        }else{
            setContentView(R.layout.activity_main_guest);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        TextView nav_headerText = (TextView) findViewById(R.id.nav_title);



        Fragment fragment = new CategoryFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = null;
        if(email.equals("Гость")){
            switch(id) {
                case R.id.nav_login:
                    intent = new Intent(this, LoginActivity.class);
                    break;
                case R.id.nav_favorites:
                    fragment = new FavoritesFragment();
                    break;
                case R.id.nav_search:
                    fragment = new SearchFragment();
                    break;
                default:
                    fragment = new CategoryFragment();
            }
        }else{
            switch (id){
                case R.id.nav_favorites:
                    fragment = new FavoritesFragment();
                    break;
                case R.id.nav_search:
                    fragment = new SearchFragment();
                    break;
                case R.id.nav_logout:

                    ContentValues userValues = new ContentValues();
                    userValues.put("USER_NAME", "user");
                    userValues.put("ISLOGIN", "0");
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

                    intent = new Intent(this, LoginActivity.class);
                    break;
                default:
                    fragment = new CategoryFragment();

            }
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }else{
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*  Выход по кнопке назад
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        super.onKeyUp(keyCode, event);

        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setTitle("Выйти из приложения?")
                    .setMessage("Вы действительно хотите выйти?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent i = new Intent(Intent.ACTION_MAIN);
                            i.addCategory(Intent.CATEGORY_HOME);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                    }).create().show();
        }
        return false;
    }
    */
}
