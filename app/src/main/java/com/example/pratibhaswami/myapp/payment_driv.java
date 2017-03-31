package com.example.pratibhaswami.myapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;
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

public class payment_driv extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPref" ;
    public static final String Name = "nameKey";
    public static final String Userid = "idKey";
    SharedPreferences sharedpreferences;

    private static Button button_rate;
    private static RatingBar rating_b;
    String distance,startTime;
    String actual_fare;
    TextView actual_fare1;
    private CheckBox Paid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_driv);
        Intent intent = getIntent();
        distance=intent.getStringExtra("distance");
        startTime=intent.getStringExtra("startTime");
        actual_fare1=(TextView)findViewById(R.id.textView2);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        rating_b = (RatingBar) findViewById(R.id.ratingBar);
        button_rate = (Button) findViewById(R.id.button);
        getStatus();
        //onButtonClickListener();

        Paid = (CheckBox)findViewById(R.id.checkBox);

        Paid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    RequestQueue rq = Volley.newRequestQueue(getBaseContext());
                    String url = "http://192.168.137.103:8000/paid";
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
                            String value = sharedpreferences.getString(Userid, "");

                            params.put("id",value);
                            return params;
                        }
                    };
                    rq.add(sr);

                }


            }});
    }

    /**public void onButtonClickListener() {


        button_rate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                        String value = sharedpreferences.getString(Userid, "");
                        String url ="http://192.168.137.103:8000/request/?rating=" + String.valueOf(rating_b.getRating()) + "&userid=" + value;

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                //error.printStackTrace();

                            }
                        });
// Add the request to the RequestQueue.
                        queue.add(stringRequest);

                    }


                });
    }**/

    public void getStatus() {
        RequestQueue rq = Volley.newRequestQueue(getBaseContext());
        String url = "http://192.168.137.103:8000/actualfare";
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
                params.put("distance",distance );
                params.put("startTime",startTime );
                return params;
            }
        };
        rq.add(sr);
    }

    private void showJSON(String json) throws JSONException {

        JSONObject reader = new JSONObject(json);
        actual_fare = reader.getString("actual_fare");
        actual_fare1.setText(actual_fare);
    }
}
