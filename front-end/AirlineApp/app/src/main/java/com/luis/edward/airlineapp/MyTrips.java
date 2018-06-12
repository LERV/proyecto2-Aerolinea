package com.luis.edward.airlineapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;

public class MyTrips extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        //-----------------------------------------------------------------
        //-----------------------------------------------------------------
        array_origin_places_next = new ArrayList<String>();
        array_destiny_places_next = new ArrayList<String>();
        array_date_next = new ArrayList<String>();
        array_km_next = new ArrayList<String>();

        array_origin_places_next.add("Liberia");
        array_origin_places_next.add("Texas");
        array_destiny_places_next.add("The Angels");
        array_destiny_places_next.add("New York");
        array_date_next.add("15/6/2018");
        array_date_next.add("20/6/2018");
        array_km_next.add("4000");
        array_km_next.add("2000");

        array_origin_places_history = new ArrayList<String>();
        array_destiny_places_history = new ArrayList<String>();
        array_date_history = new ArrayList<String>();
        array_km_history = new ArrayList<String>();

        array_origin_places_history.add("Chicago");
        array_origin_places_history.add("San Jose");
        array_destiny_places_history.add("New York");
        array_destiny_places_history.add("Miami");
        array_date_history.add("10/6/2018");
        array_date_history.add("2/3/2018");
        array_km_history.add("6000");
        array_km_history.add("3000");

        gridView_next_trips = findViewById(R.id.grid_next_trips);
        GridAdapter_Trips adapter1 = new GridAdapter_Trips(MyTrips.this,array_origin_places_next,array_destiny_places_next,array_date_next,array_km_next);
        gridView_next_trips.setAdapter(adapter1);

        gridView_history_trips = findViewById(R.id.grid_history_trips);
        GridAdapter_Trips adapter2 = new GridAdapter_Trips(MyTrips.this,array_origin_places_history,array_destiny_places_history,array_date_history,array_km_history);
        gridView_history_trips.setAdapter(adapter2);
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
            go_account();

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void go_account() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}
