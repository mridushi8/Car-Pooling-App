package com.example.pratibhaswami.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pratibhaswami on 22/01/17.
 */

public class MainActivity2 extends AppCompatActivity {
    private Button b_get;
    private TrackGPS gps;
    double longitude;
    double latitude;
    String lat_source,long_source,final_distance,passenger_id;
    double lats,longs,theta,dist;

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        //lat_source = intent.getStringExtra("lat_source");
        //long_source = intent.getStringExtra("long_source");
        passenger_id=intent.getStringExtra("passenger_id");
        //lats=Double.parseDouble(lat_source);
        //longs=Double.parseDouble(long_source);
        lats=89.9;
        longs=90.8;

        b_get = (Button) findViewById(R.id.get);


        b_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gps = new TrackGPS(MainActivity2.this);


                if (gps.canGetLocation()) {


                    longitude = gps.getLongitude();
                    latitude = gps.getLatitude();
                     theta = longitude - longs;
                     dist = Math.sin(deg2rad(latitude)) * Math.sin(deg2rad(lats)) + Math.cos(deg2rad(latitude)) * Math.cos(deg2rad(lats)) * Math.cos(deg2rad(theta));
                    dist = Math.acos(dist);
                    dist = rad2deg(dist);
                    dist = dist * 60 * 1.1515;
                    dist = dist * 1.609344;
                    final_distance=Double.toString(dist);

                  /* double dlon = longitude - longs;
                   double dlat = latitude - lats;
                    double a = ((Math.sin(dlat/2))^2 + Math.cos(lats) * Math.cos(latitude) * (Math.sin(dlon/2))^2);
                    double c = 2 * a*Math.tan*2*( Math.sqrt(a), Math.sqrt(1-a) );
                    d = R * c (where R is the radius of the Earth)*/

                    Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
                   Respond();
                } else {

                    gps.showSettingsAlert();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }

    public void Respond() {
        //loading = ProgressDialog.show(this, "Fetching Driver Details", "Please wait..", false, false);
        RequestQueue rq = Volley.newRequestQueue(getBaseContext());
        String url = "http://192.168.1.8:8000/api/response";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String rsp) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("request_id", "2");
                params.put("id","21");
                params.put("location",final_distance);
                return params;
            }
        };
        rq.add(sr);
    }
}
