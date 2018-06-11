package com.luis.edward.airlineapp;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText textName;
    EditText textLastName;

    EditText textEmail;
    EditText textPassword;

    UsersController userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //--------------------------------------------------
        userData = UsersController.getInstance();

        //Log.d("Name USER",userSession.getNameUser().toString());


        textName= findViewById(R.id.editTextName);
        textLastName= findViewById(R.id.EditTextLastName);
        textEmail= findViewById(R.id.EditTextViewEmail);
        textPassword= findViewById(R.id.EditTextPassword);

        Log.d("ID_llego",userData.getId());

        textName.setText(userData.getName());
        textLastName.setText(userData.getLast_name());
        //Log.d("Emai ES:",userSession.getEm);
        textEmail.setText(userData.getEmail());
        textPassword.setText(userData.getPassword());
        String urlImageProfile=userData.getProfile_picture();

        //Agregar foto de perfil a View image
        ImageView image = findViewById(R.id.imageProfile);
        Picasso.get().load(urlImageProfile).into(image);
    }

    public void updateProfile(View view)
    {

        userData.putUser(this,userData.getId(),textName.getText().toString(),textLastName.getText().toString(),textEmail.getText().toString(),textPassword.getText().toString());
        Toast.makeText(this, "Your information has been updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
