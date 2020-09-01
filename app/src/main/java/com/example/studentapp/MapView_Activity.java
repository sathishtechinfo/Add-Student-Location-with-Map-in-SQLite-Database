package com.example.studentapp;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapView_Activity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    Geocoder geo;
    TextView txtMarkers;
    Button btntype1, btntype2;
    DatabaseHelper dbHelper;

    Button btnAdd;
    double lat, longit;
    List<Student> studentList = new ArrayList<>();
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        txtMarkers = (TextView) findViewById(R.id.txtMarkerText);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setVisibility(View.GONE);
        dbHelper = new DatabaseHelper(this);
        studentList = dbHelper.getAllStudent("");

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (mMap != null)
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.latitude = lat;
                AppUtils.longitude = longit;
                finish();
            }
        });
    }


    public static Bitmap createCustomMarker(Context context,  String resource, String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);

        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        Bitmap myBitmap = BitmapFactory.decodeFile(resource);

        markerImage.setImageBitmap(myBitmap);
        TextView txt_name = (TextView)marker.findViewById(R.id.name);
        txt_name.setText(_name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            geo = new Geocoder(MapView_Activity.this, Locale.getDefault());
            for (int i = 0; i < studentList.size(); i++) {
                String[] str = studentList.get(i).getLocation().split(",");
                LatLng latLng = new LatLng(Double.parseDouble(str[0]), Double.parseDouble(str[1]));

                if(studentList.get(i).getImagePath()!=null) {
                    File imgFile = new File(studentList.get(i).getImagePath());
                    if (imgFile.exists()) {

                        try {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                            Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
                            Canvas canvas1 = new Canvas(bmp);

// paint defines the text color, stroke width and size
                            Paint color = new Paint();
                            color.setTextSize(35);
                            color.setColor(Color.BLACK);

// modify canvas
                            canvas1.drawBitmap(myBitmap, 0, 0, color);
                            canvas1.drawText("User Name!", 30, 40, color);

                            mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .icon(BitmapDescriptorFactory.fromBitmap(
                                            createCustomMarker(MapView_Activity.this, studentList.get(i).getImagePath(), studentList.get(i).getUsername())))).setTitle(studentList.get(i).getSchool());
                   /*  mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.fromBitmap(myBitmap))
                             .anchor(0.5f, 1));*/


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
// add marker to Map

                        //mMap.addMarker(new MarkerOptions().position(latLng));
                    }
                }


//            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng) {
//                    try {
//                        if (geo == null)
//                            geo = new Geocoder(MapView_Activity.this, Locale.getDefault());
//                        List<Address> address = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                        mMap.clear();
//                        mMap.addMarker(new MarkerOptions().position(latLng).title("Name:" + address.get(0).getCountryName()
//                                + ". Address:" + address.get(0).getAddressLine(0)));
//                        lat=latLng.latitude;
//                        longit=latLng.longitude;
//                        txtMarkers.setText(""+latLng.latitude);
//
//                       /* if (address.size() > 0) {
//                            mMap.addMarker(new MarkerOptions().position(latLng).title("Name:" + address.get(0).getCountryName()
//                                    + ". Address:" + address.get(0).getAddressLine(0)));
//                            txtMarkers.setText("Name:" + address.get(0).getCountryName()
//                                    + ". Address:" + address.get(0).getAddressLine(0));
//                        }*/
//                    } catch (IOException ex) {
//                        if (ex != null)
//                            Toast.makeText(MapView_Activity.this, "Error:" + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//
//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//                    lat=marker.getPosition().latitude;
//                    longit=marker.getPosition().longitude;
//                    txtMarkers.setText(marker.getTitle().toString() + " Lat:" + marker.getPosition().latitude + " Long:" + marker.getPosition().longitude);
//                    return false;
//                }
//            });
            }




        }
    }
}