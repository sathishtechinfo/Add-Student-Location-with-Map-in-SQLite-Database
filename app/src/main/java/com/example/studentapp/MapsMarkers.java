package com.example.studentapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsMarkers extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    Geocoder geo;
    TextView txtMarkers;
    Button btntype1,btntype2;

    Button btnAdd;
    double lat,longit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        txtMarkers = (TextView) findViewById(R.id.txtMarkerText);
        btnAdd=findViewById(R.id.btn_add);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (mMap != null)
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


      btnAdd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                AppUtils.latitude=lat;
                AppUtils.longitude=longit;
                finish();
          }
      });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            geo = new Geocoder(MapsMarkers.this, Locale.getDefault());

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    try {
                        if (geo == null)
                            geo = new Geocoder(MapsMarkers.this, Locale.getDefault());
                        List<Address> address = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        mMap.clear();
                       mMap.addMarker(new MarkerOptions().position(latLng).title("Name:" + address.get(0).getCountryName()
                                + ". Address:" + address.get(0).getAddressLine(0)));
                        lat=latLng.latitude;
                        longit=latLng.longitude;
                        txtMarkers.setText(""+latLng.latitude);

                       /* if (address.size() > 0) {
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Name:" + address.get(0).getCountryName()
                                    + ". Address:" + address.get(0).getAddressLine(0)));
                            txtMarkers.setText("Name:" + address.get(0).getCountryName()
                                    + ". Address:" + address.get(0).getAddressLine(0));
                        }*/
                    } catch (IOException ex) {
                        if (ex != null)
                            Toast.makeText(MapsMarkers.this, "Error:" + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    lat=marker.getPosition().latitude;
                    longit=marker.getPosition().longitude;
                    txtMarkers.setText(marker.getTitle().toString() + " Lat:" + marker.getPosition().latitude + " Long:" + marker.getPosition().longitude);
                    return false;
                }
            });
        }

    }
}