package com.example.hikehub;

import static android.graphics.Color.rgb;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Casual_Start extends SuperScreen implements OnMapReadyCallback, TaskLoadedCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private  static int timesMapClicked = 0;
    private GoogleMap gMap;
    boolean routeStarted;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button startRoute;
    Button leaveRoute;
    EditText showDistance;
    TextView textView;
    LatLng location;
    LatLng destination;
    Polyline currentPolyLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_casual_start);

        routeStarted = false;
        startRoute = findViewById(R.id.createCasual);
        showDistance = findViewById(R.id.casualDistance);
        textView = findViewById(R.id.casualText);
        leaveRoute = findViewById(R.id.leaveCasual);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        startRoute.setOnClickListener(view -> {
            if (destination.equals(null)) {
                Toast.makeText(this, "Please enter a destination.", Toast.LENGTH_SHORT).show();
            } else {
                routeStarted = true;
                leaveRoute.setVisibility(VISIBLE);
                startRoute.setVisibility(INVISIBLE);
                showDistance.setClickable(false);
                textView.setText("Remaining Distance");
                createRoute();
            }
        });

        leaveRoute.setOnClickListener(view -> {

        });
    }

    private void createRoute() {
        new FetchURL(Casual_Start.this).execute("https://maps.googleapis.com/maps/api/directions/json?destination=" + destination.latitude + "," + destination.longitude + "&mode=walking&origin=" + location.latitude + "," + location.longitude + "&key=" + getString(R.string.my_API_key), "walking");
    }

    private void getLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(Casual_Start.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                    timesMapClicked++;
                    if (timesMapClicked > 1) {
                        gMap.clear();
                        gMap.addCircle(new CircleOptions().center(location).radius(30).fillColor(rgb(255,255,255)).strokeWidth(0).visible(true));
                        gMap.addCircle(new CircleOptions().center(location).radius(22).fillColor(rgb(50,50,255)).strokeWidth(0).visible(true));
                    }
                    destination = latLng;
                    gMap.addMarker(new MarkerOptions().position(latLng).title("Destination").visible(true));
            }
        });

        gMap.addCircle(new CircleOptions().center(location).radius(30).fillColor(rgb(255,255,255)).strokeWidth(0).visible(true));
        gMap.addCircle(new CircleOptions().center(location).radius(22).fillColor(rgb(50,50,255)).strokeWidth(0).visible(true));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setCompassEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission is denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyLine != null) {
            currentPolyLine.remove();
        }else {
            currentPolyLine = gMap.addPolyline( (PolylineOptions) values[0]);
        }
    }
}