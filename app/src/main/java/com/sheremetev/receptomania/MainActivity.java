package com.sheremetev.receptomania;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sheremetev.receptomania.Model.NullPage;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView nav_headerText;
    boolean isFin = false;

    public final static String EXTRA_EMAIL_MESSAGE = "email";
    String email;
    boolean isCopyRightClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = (String)getIntent().getExtras().get(EXTRA_EMAIL_MESSAGE);
        if(email != null){
            if(email.equals("Гость")){
                setContentView(R.layout.activity_main_guest);
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }else{
                setContentView(R.layout.activity_main);
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }else{
            setContentView(R.layout.activity_main_guest);
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        NullPage.email = email;

        View header = navigationView.getHeaderView(0);

        nav_headerText = (TextView) header.findViewById(R.id.nav_title);

        nav_headerText.setText(NullPage.email);

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
                    NullPage.email = null;
                    intent = new Intent(this, LoginActivity.class);
                    isCopyRightClick = false;
                    break;
                case R.id.nav_favorites:
                    fragment = new FavoritesFragment();
                    isCopyRightClick = false;
                    break;
                case R.id.nav_search:
                    fragment = new SearchFragment();
                    isCopyRightClick = false;
                    break;
                case R.id.shutdown:
                    isCopyRightClick = false;
                    this.finishAffinity();
                    System.exit(0);
                    break;
                case R.id.copyright:
                    isCopyRightClick = true;
                    break;
                default:
                    fragment = new CategoryFragment();
            }
        }else{
            switch (id){
                case R.id.nav_favorites:
                    fragment = new FavoritesFragment();
                    isCopyRightClick = false;
                    break;
                case R.id.nav_profile:
                    fragment = new ProfileFragment();
                    isCopyRightClick = false;
                    break;
                case R.id.nav_myRecipes:
                    fragment = new MyRecipesFragment();
                    isCopyRightClick = false;
                    break;
                case R.id.nav_addRecipe:
                    fragment = new AddRecipeFragment();
                    isCopyRightClick = false;
                    break;
                case R.id.shutdown:
                    isCopyRightClick = false;
                    this.finishAffinity();
                    System.exit(0);
                    break;
                case R.id.nav_search:
                    fragment = new SearchFragment();
                    break;
                case R.id.copyright:
                    isCopyRightClick = true;
                    break;
                case R.id.nav_logout:
                    NullPage.email = null;
                    isCopyRightClick = false;
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
        if(!isCopyRightClick){
            if(fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }else{
                startActivity(intent);
            }
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



}
