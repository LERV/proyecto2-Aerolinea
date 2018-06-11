package com.luis.edward.airlineapp;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Adapter;
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

public class DetalleClasses extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private ImageView imageUser;
    private TextView nameUser;
    private TextView emailUser;
    private View navHeader;

    private ArrayList array_economy;
    private ArrayList array_business;
    private ArrayList array_premium;

    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private TextView txt5;
    private TextView txt6;

    private int num_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_classes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        //-------------------------------------------------------

        Intent intent = getIntent();
        num_class = intent.getIntExtra("clase",0);
        Log.d("num",String.valueOf(num_class));

        array_economy = new ArrayList<String>();
        array_business = new ArrayList<String>();
        array_premium = new ArrayList<String>();
        txt1 = findViewById(R.id.textView1);
        txt2 = findViewById(R.id.textView2);
        txt3 = findViewById(R.id.textView3);
        txt4 = findViewById(R.id.textView4);
        txt5 = findViewById(R.id.textView5);
        txt6 = findViewById(R.id.textView6);

        array_economy.add("6kg carry-on baggage");
        array_economy.add("20kg normal baggage");
        array_economy.add("Standard food");
        array_economy.add("Standard drinks");
        array_economy.add("Personalized entertainment");
        array_economy.add("Changes are allowed with charges");

        array_business.add("10kg carry-on baggage");
        array_business.add("25kg normal baggage");
        array_business.add("Selected food");
        array_business.add("Alcoholic beverages");
        array_business.add("Individual screen");
        array_business.add("Changes are allowed with charges");

        array_premium.add("15kg carry-on baggage");
        array_premium.add("No normal baggage weight limit");
        array_premium.add("Food and drink refill are allowed");
        array_premium.add("All movies are unlocked");
        array_premium.add("Bigger seat");
        array_premium.add("Changes without charges");

        llena();

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
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
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
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLoginScreen();
                }else{
                    Toast.makeText(DetalleClasses.this,"Session could not be closed",Toast.LENGTH_SHORT).show();
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            go_account();

        } else if (id == R.id.nav_share) {
            log_out();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void llena() {
        if(num_class==1){
            txt1.setText(array_economy.get(0).toString());
            txt2.setText(array_economy.get(1).toString());
            txt3.setText(array_economy.get(2).toString());
            txt4.setText(array_economy.get(3).toString());
            txt5.setText(array_economy.get(4).toString());
            txt6.setText(array_economy.get(5).toString());
        }
        if(num_class==2){
            txt1.setText(array_business.get(0).toString());
            txt2.setText(array_business.get(1).toString());
            txt3.setText(array_business.get(2).toString());
            txt4.setText(array_business.get(3).toString());
            txt5.setText(array_business.get(4).toString());
            txt6.setText(array_business.get(5).toString());
        }
        if(num_class==3){
            txt1.setText(array_premium.get(0).toString());
            txt2.setText(array_premium.get(1).toString());
            txt3.setText(array_premium.get(2).toString());
            txt4.setText(array_premium.get(3).toString());
            txt5.setText(array_premium.get(4).toString());
            txt6.setText(array_premium.get(5).toString());
        }
    }

    private void go_account() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}

