package com.luis.edward.airlineapp;

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

public class FlightsList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    GridView gridView_flights;
    ArrayList<String> array_prices;
    ArrayList<String> array_origin_places;
    ArrayList<String> array_destiny_places;
    ArrayList<String> array_flight_departure;
    ArrayList<String> array_flight_arrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights_list);
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

        //--------------------------------------------------------------------
        //--------------------------------------------------------------------

        array_prices = new ArrayList<String>();
        array_origin_places = new ArrayList<String>();
        array_destiny_places = new ArrayList<String>();
        array_flight_departure = new ArrayList<String>();
        array_flight_arrival = new ArrayList<String>();

        array_prices.add("500");
        array_prices.add("250");
        array_prices.add("300");
        array_prices.add("699");
        array_prices.add("1000");
        array_origin_places.add("San Jose");
        array_origin_places.add("Budapest");
        array_origin_places.add("Texas");
        array_origin_places.add("Liberia");
        array_origin_places.add("Helsinki");

        array_destiny_places.add("Miami");
        array_destiny_places.add("Munich");
        array_destiny_places.add("San Jose");
        array_destiny_places.add("Mexico DF");
        array_destiny_places.add("Barcelona");

        array_flight_departure.add("13:00");
        array_flight_departure.add("21:30");
        array_flight_departure.add("9:00");
        array_flight_departure.add("7:00");
        array_flight_departure.add("6:00");

        array_flight_arrival.add("14:00");
        array_flight_arrival.add("23:00");
        array_flight_arrival.add("12:45");
        array_flight_arrival.add("11:15");
        array_flight_arrival.add("10:00");

        gridView_flights = findViewById(R.id.gridView_listFlights);
        GridAdapter adapter = new GridAdapter(FlightsList.this,array_prices,array_origin_places,array_destiny_places,array_flight_departure,array_flight_arrival);
        gridView_flights.setAdapter(adapter);


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
