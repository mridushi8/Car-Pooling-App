package com.example.pratibhaswami.myapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class payment_pass extends AppCompatActivity {

    public static final String Name = "nameKey";

    SharedPreferences sharedpreferences;
    public static final String Userid = "idKey";
    public static final String MyPREFERENCES = "MyPref" ;

    private static Button button_rate;
    private static RatingBar rating_b;

    String distance,startTime, value;
    String actual_fare;
    private Button end;
    TextView actual_fare1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_pass);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("LIFT");
        }

        //Intent intent = getIntent();
        //distance=intent.getExtras().getString("distance");
        //startTime=intent.getExtras().getString("startTime");
        actual_fare1=(TextView)findViewById(R.id.textView);
        button_rate  = (Button) findViewById(R.id.button);
        rating_b  = (RatingBar) findViewById(R.id.ratingBar);
        end=(Button)findViewById(R.id.end);

        rating_b.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getApplicationContext(), "You have rated : " + String.valueOf(rating ), Toast.LENGTH_LONG).show();
            }
        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        value = sharedpreferences.getString(Userid, "");
        getStatus();
        onButtonClickListener();
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue rq = Volley.newRequestQueue(getBaseContext());
                String url = Constants.url + "paid";
                StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String rsp) {
                        if(rsp.equals("True"))
                        {
                            Intent i = new Intent(payment_pass.this, afterlogin.class);
                            startActivity(i);
                        }
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
                        String value = sharedpreferences.getString(Userid, "");

                        params.put("id",value);
                        return params;
                    }
                };
                rq.add(sr);

            }
        });
    }
    @Override
    public void onBackPressed() { }


    public void onButtonClickListener() {


        button_rate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getStatus();
                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                        final String value = sharedpreferences.getString(Userid, "");
                        String url =Constants.url + "rating";

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                //error.printStackTrace();

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("rating", String.valueOf(rating_b.getRating()));
                                params.put("userid", value);
                                return params;
                            }
                        };
// Add the request to the RequestQueue.
                        queue.add(stringRequest);

                    }


                });
    }

    public void getStatus() {
        return;
        /*
        RequestQueue rq = Volley.newRequestQueue(getBaseContext());
        String url = Constants.url + "actualfare";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String rsp) {
                try {
                    showJSON(rsp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                Log.d("distance", String.valueOf(distance));
                Log.d("startTime", startTime);
                params.put("distance",distance );
                params.put("startTime",startTime );
                return params;
            }
        };
        rq.add(sr); */
    }

    private void showJSON(String json) throws JSONException {

        JSONObject reader = new JSONObject(json);
        actual_fare = reader.getString("actual_fare");
        actual_fare1.setText(actual_fare);






    }
}
