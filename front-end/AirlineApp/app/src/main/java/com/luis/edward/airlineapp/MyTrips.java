package com.luis.edward.airlineapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
import android.widget.GridView;
import android.widget.ImageView;
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

public class MyTrips extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    GridView gridView_next_trips;
    GridView gridView_history_trips;

    ArrayList array_origin_places_next;
    ArrayList array_destiny_places_next;
    ArrayList array_date_next;
    ArrayList array_km_next;

    ArrayList array_origin_places_history;
    ArrayList array_destiny_places_history;
    ArrayList array_date_history;
    ArrayList array_km_history;

    ArrayList vuelos_user;



    private GoogleApiClient googleApiClient;
    private ImageView imageUser;
    private TextView nameUser;
    private TextView emailUser;
    private View navHeader;

    UsersController userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
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

        //-----------------------------------------------------------------
        //-----------------------------------------------------------------
        array_origin_places_next = new ArrayList<String>();
        array_destiny_places_next = new ArrayList<String>();
        array_date_next = new ArrayList<String>();
        array_km_next = new ArrayList<String>();

/*        array_origin_places_next.add("Liberia");
        array_origin_places_next.add("Texas");
        array_destiny_places_next.add("The Angels");
        array_destiny_places_next.add("New York");
        array_date_next.add("15/6/2018");
        array_date_next.add("20/6/2018");
        array_km_next.add("4000");
        array_km_next.add("2000");*/

        array_origin_places_history = new ArrayList<String>();
        array_destiny_places_history = new ArrayList<String>();
        array_date_history = new ArrayList<String>();
        array_km_history = new ArrayList<String>();

        /*array_origin_places_history.add("Chicago");
        array_origin_places_history.add("San Jose");
        array_destiny_places_history.add("New York");
        array_destiny_places_history.add("Miami");
        array_date_history.add("10/6/2018");
        array_date_history.add("2/3/2018");
        array_km_history.add("6000");
        array_km_history.add("3000");*/


        vuelos_user =  new ArrayList<String>();
        //VUELOS USER = DATA. IDS
        seleccionaVuelos();

        gridView_next_trips = findViewById(R.id.grid_next_trips);
        GridAdapter_Trips adapter1 = new GridAdapter_Trips(MyTrips.this,array_origin_places_next,array_destiny_places_next,array_date_next,array_km_next);
        gridView_next_trips.setAdapter(adapter1);

        gridView_history_trips = findViewById(R.id.grid_history_trips);
        GridAdapter_Trips adapter2 = new GridAdapter_Trips(MyTrips.this,array_origin_places_history,array_destiny_places_history,array_date_history,array_km_history);
        gridView_history_trips.setAdapter(adapter2);
    }

    private void seleccionaVuelos() {
        for(int i=0; i<vuelos_user.size();i++){
            for(int j=0; j<Search.all_flights_list.size();j++){
                ArrayList vueloEspecifico = Search.all_flights_list.get(j);
                if(vuelos_user.get(i) == vueloEspecifico.get(0)){
                    array_origin_places_history.add(vueloEspecifico.get(2));
                    array_destiny_places_history.add(vueloEspecifico.get(3));
                    array_date_history.add(vueloEspecifico.get(4));
                    array_km_history.add(vueloEspecifico.get(10));
                }
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
        } else if (id == R.id.nav_gallery) {
            go_map();

        } else if (id == R.id.nav_slideshow) {
            go_my_trips();

        } else if (id == R.id.nav_manage) {
            go_account();

        } else if (id == R.id.nav_share)
            log_out();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public void log_out(){
        userData.setUserSessionState(false);
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLoginScreen();
                }else{
                    Toast.makeText(MyTrips.this,"Session could not be closed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
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

    private void go_account() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
