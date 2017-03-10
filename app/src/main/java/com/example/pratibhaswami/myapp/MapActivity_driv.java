package com.example.pratibhaswami.myapp;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity_driv extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "Bla";
    PlaceAutocompleteFragment autocompleteFragment1;
    private GoogleApiClient client;

    //double destlat;
    //double destlog;

    String dest;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        autocompleteFragment1 =
                (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);

        autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName());
                String location = (String) place.getAddress();
                //dest = (String) place.getAddress();
                Geocoder geocoder = new Geocoder(getBaseContext());
                try {
                    List addressList = geocoder.getFromLocationName(location, 1);
                    if (addressList != null && addressList.size()>0){
                        Address address = (Address) addressList.get(0);
                        //destlat = address.getLatitude();
                        //destlog = address.getLongitude();
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }


        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }







    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng Delhi = new LatLng(28.614055, 77.211033);
        mMap.addMarker(new MarkerOptions().position(Delhi).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Delhi));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    /***public void onClick(View view) {

     RequestQueue MyRequestQueue = Volley.newRequestQueue(getBaseContext());
     String url = "http://192.168.1.4:8000/api/estimatefare/?origin=" + origin + "&dest=" + dest;
     StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
    Context context = getApplicationContext();
    Toast.makeText(context, R.string.popup, LENGTH_LONG)
    .show();
    }
    }, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
    Context context = getApplicationContext();
    Toast.makeText(context, R.string.fail, LENGTH_LONG)
    .show();
    error.printStackTrace();
    }
    });
     MyRequestQueue.add(MyStringRequest);

     }***/

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page").setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}