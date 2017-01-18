package com.example.pratibhaswami.myapplication;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button b_get;
    private TrackGPS gps;
    double longitude;
    double latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_get = (Button)findViewById(R.id.get);



        b_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gps = new TrackGPS(MainActivity.this);


                if(gps.canGetLocation()){


                    longitude = gps.getLongitude();
                    latitude = gps .getLatitude();

                    Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
                }
                else
                {

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
}