package com.example.pratibhaswami.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
public static final String MyPREFERENCES = "MyPref" ;
    public static final String Name = "nameKey";
    public static final String Userid = "idKey";
    SharedPreferences sharedpreferences;



public class payment_pass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_pass);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        
    }
     
    public void onButtonClickListener() {
        rating_b = (RatingBar) findViewById(R.id.ratingBar);
        button_rate = (Button) findViewById(R.id.button);

        button_rate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                        String value = sharedpreferences.getString(Userid, "");
                        String url = "http://localhost:8000/api/request/?rating=" + String.valueOf(rating_b.getRating()) + "&userid=" + value;

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                error.printStackTrace();

                            }
                        });
// Add the request to the RequestQueue.
                        queue.add(stringRequest);

                    }


                });
    }
}
