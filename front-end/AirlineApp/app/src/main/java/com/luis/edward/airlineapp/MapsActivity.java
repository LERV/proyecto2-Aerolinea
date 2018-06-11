package com.luis.edward.airlineapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    int which_txt_field;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        which_txt_field = intent.getIntExtra("txt_view",0);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        //move camara to Costa Rica and zoom 7
        LatLng costa_rica = new LatLng(29.6194427, -92.3466717);
        float zoomlevel = 3;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(costa_rica, zoomlevel));


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(10.0275321, -84.2224416);
        mMap.addMarker(new MarkerOptions().position(sydney).title("San Jose"));
        LatLng los_angeles = new LatLng(34.0207305, -118.6919273);
        mMap.addMarker(new MarkerOptions().position(los_angeles).title("Los Angeles"));
        LatLng new_york = new LatLng(40.6976701, -74.2598722);
        mMap.addMarker(new MarkerOptions().position(new_york).title("New York"));
        LatLng dallas = new LatLng(32.8209296, -97.0117378);
        mMap.addMarker(new MarkerOptions().position(dallas).title("Dallas"));
        LatLng miami = new LatLng(25.7825453, -80.2994992);
        mMap.addMarker(new MarkerOptions().position(miami).title("Miami"));
        LatLng houston = new LatLng(29.8174782, -95.6814882);
        mMap.addMarker(new MarkerOptions().position(houston).title("Houston"));
        LatLng chicago = new LatLng(41.8339042, -88.0121545);
        mMap.addMarker(new MarkerOptions().position(chicago).title("Chicago"));



        mMap.setOnInfoWindowClickListener(getInfoWindowClickListener());
    }


    public GoogleMap.OnInfoWindowClickListener getInfoWindowClickListener(){
        return new GoogleMap.OnInfoWindowClickListener(){
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.d("rasta",marker.getTitle());
                if(which_txt_field==1){
                    Search.origin.setText(marker.getTitle());
                    finish();
                }else{
                    if(which_txt_field==2){
                        Search.destiny.setText(marker.getTitle());
                        finish();
                    }else{
                        Log.d("rasta","error");
                    }
                }
            }
        };
    }

    @Override
    public void finish() {
        super.finish();

    }
}
