package com.luis.edward.airlineapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

public class AmountPassagers extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private ImageView imageUser;
    private TextView nameUser;
    private TextView emailUser;
    private View navHeader;
    private RadioGroup radioGroup;
    private RadioButton rb_economy;
    private RadioButton rb_business;
    private RadioButton rb_premium;

    private TextView textView_see_clases;
    UsersController userData;

    String num_adults;
    String num_children;
    String selected_clase;
    ArrayList<String> info_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_passagers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //-----------------------------------------------------
        //---------Para conectar a google silenciosamente y cargar foto nombre email
        navHeader = navigationView.getHeaderView(0);
        imageUser = (ImageView) navHeader.findViewById(R.id.imageViewGoogle_user);
        nameUser = (TextView) navHeader.findViewById(R.id.nameGoogle_user);
        emailUser = (TextView) navHeader.findViewById(R.id.emailGoogle_user);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        //-------------------------------------------------------
        //-------------------Number picker Adults------------------------
        com.shawnlin.numberpicker.NumberPicker numberPicker_adults =  findViewById(R.id.number_picker_adults);
        numberPicker_adults.setOnValueChangedListener(new com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(com.shawnlin.numberpicker.NumberPicker picker, int oldVal, int newVal) {
                Log.d("numero_adult",String.valueOf(newVal));
                num_adults =  String.valueOf(newVal);
            }
        });
        //-------------------------------------------------------
        //-------------------Number picker Children------------------------
        com.shawnlin.numberpicker.NumberPicker numberPicker_children =  findViewById(R.id.number_picker_children);
        numberPicker_children.setOnValueChangedListener(new com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(com.shawnlin.numberpicker.NumberPicker picker, int oldVal, int newVal) {
                Log.d("numero_children",String.valueOf(newVal));
                num_children = String.valueOf(newVal);
            }
        });
        //-------------------------------------------------------
        //-------------------Radio Buttons----------------------

        radioGroup = findViewById(R.id.radioGroup_class);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d("perro",String.valueOf(i));
                if(i==2131361968){
                    selected_clase = "economy";
                }
                if(i==2131361967){
                    selected_clase = "business";
                }
                if(i==2131361969){
                    selected_clase = "premium";
                }
            }
        });

        //------------------------------------------------------
        textView_see_clases = findViewById(R.id.textView_class_detail);

        num_adults = "1";
        num_children = "0";
        selected_clase = "economy";



    }

    @Override
    protected void onStart() {
        super.onStart();

        //Se descarga la informacion sobre los usuario nuevamente
        userData = UsersController.getInstance();
        if (userData.getUserSessionState())
        {
            Log.d("Rino", "Va a descargar User del start");
            userData = UsersController.getInstance();
            userData.downloadDataFromAPi(getCacheDir());
            SystemClock.sleep(3000);
            userData.setSessionUser(userData.getIdSession());

            nameUser.setText(userData.getName());
            emailUser.setText(userData.getEmail());
            //para cargar la foto de la persona
            if (userData.getProfile_picture() == "null") {
                Log.d("perro", "foto es null");
                imageUser.setImageResource(R.drawable.plane_icon);
            }
            else
            {
                Glide.with(this).load(userData.getProfile_picture()).into(imageUser);
            }
        }
        else {

            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if(opr.isDone()){
                GoogleSignInResult result = opr.get();
                Log.d("gato","Llega antes del handle");
                handleSignInResult(result);
            }else{
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                        handleSignInResult(googleSignInResult);
                    }
                });
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();

            nameUser.setText(account.getDisplayName());
            emailUser.setText(account.getEmail());
            //para cargar la foto de la persona
            Glide.with(this).load(account.getPhotoUrl()).into(imageUser);

        }else{
            //goLoginScreen();
            UsersController persona = UsersController.getInstance();
            nameUser.setText(persona.getName());
            emailUser.setText(persona.getEmail());
            //para cargar la foto de la persona
            if(persona.getProfile_picture()== "null"){
                Log.d("perro","foto es null");
                imageUser.setImageResource(R.drawable.plane_icon);
            }else{
                Glide.with(this).load(persona.getProfile_picture()).into(imageUser);
            }
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            goMainActivity();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            go_map();
        } else if (id == R.id.nav_slideshow) {
            go_my_trips();

        } else if (id == R.id.nav_manage) {
            go_account();

        } else if (id == R.id.nav_share) {
            log_out();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, Search.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void go_map() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    private void go_my_trips() {
        Intent intent = new Intent(this, MyTrips.class);
        startActivity(intent);
    }

    public void log_out(){
        userData.setUserSessionState(false);
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLoginScreen();
                }else{
                    Toast.makeText(AmountPassagers.this,"Session could not be closed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void go_classes_screen(View view){
        Intent intent = new Intent(this, Classes.class);
        startActivity(intent);
    }

    public void go_flights_list(View view){
        Log.d("data",num_adults);
        Log.d("data",num_children);
        Log.d("data",selected_clase);

        Search.info_selected_user.add(num_adults);
        Search.info_selected_user.add(num_children);
        Search.info_selected_user.add(selected_clase);

        Intent intent = new Intent(this, FlightsList.class);
        startActivity(intent);
    }

    private void go_account() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}
