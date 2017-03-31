package com.example.pratibhaswami.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

    private static Activity currentActivity;
    String text;
    public static final String Userid = "idKey";
    public static final String MyPREFERENCES = "MyPref" ;
    SharedPreferences sharedpreferences;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                text = userId;

               /** if (registrationId != null)
                    text += "Google Registration Id:\n" + registrationId;
                else
                    text += "Google Registration Id:\nCould not subscribe for push";**/

                TextView textView = (TextView)findViewById(R.id.debug_view);
                textView.setText(text);
            }
        });
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        value = sharedpreferences.getString(Userid, "");
        getStatus();
    }

    public void getStatus() {
        //loading = ProgressDialog.show(this, "Fetching Driver Details", "Please wait..", false, false);
        RequestQueue rq = Volley.newRequestQueue(getBaseContext());
        String url = "http://192.168.137.103:8000/player_id";
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
                params.put("player_id",text);
                params.put("id",value);
                return params;
            }
        };
        rq.add(sr);
    }
}

