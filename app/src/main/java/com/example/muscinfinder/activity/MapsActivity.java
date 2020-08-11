package com.example.muscinfinder.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.muscinfinder.R;
import com.example.muscinfinder.modal.LatitudeLongitude;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String lattitude, longitude, name;
    public static String storedLongitude = null;
    public static String storedLatitude = null;
    public static String storedName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // new coordinate
        lattitude = storedLatitude.toString();
        longitude = storedLongitude.toString();
        name= storedName.toString();

    }

    // institute location
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<LatitudeLongitude> latLngs=new ArrayList<>();
        latLngs.add(new LatitudeLongitude(Double.parseDouble(lattitude), Double.parseDouble(longitude),name));
        CameraUpdate center, zoom;
        for (int i =0;i<latLngs.size();i++){
            center= CameraUpdateFactory.newLatLng(new LatLng(latLngs.get(i).getLat(),
                    latLngs.get(i).getLon()));
            zoom= CameraUpdateFactory.zoomTo(14);
            mMap.addMarker(new MarkerOptions().position(new LatLng(latLngs.get(i).getLat(),
                    latLngs.get(i).getLon())).title(latLngs.get(i).getMarker()));
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }

}