package com.example.pratibhaswami.myapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pratibhaswami on 27/01/17.
 */

public class MainActivity1 extends AppCompatActivity {

    private String TAG = MainActivity1.class.getSimpleName();
   // private ListView lv;
    TextView name1;
    TextView timestamp1;
    TextView destination1;
    TextView source1;
    TextView phone_no1;
    Button accept;
    Button reject;
    ProgressDialog loading;
    String name;
    String phone_no;
    String timestamp;
    String source;
    String destination;
    // private Thread thread;
    String passenger_id;
    String lat_source;
    String long_source;
    public static final String Userid = "idKey", MyPREFERENCES = "MyPref";
    SharedPreferences sharedpreferences;
    String value;
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Intent intent = getIntent();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        value = sharedpreferences.getString(Userid, "");
        passenger_id = intent.getStringExtra("passenger_id");
        Log.d("Passenger id",passenger_id);

        //contactList = new ArrayList<>();
       // lv = (ListView) findViewById(R.id.list);
        name1=(TextView)findViewById(R.id.name);
        timestamp1=(TextView)findViewById(R.id.timestamp);
        destination1=(TextView)findViewById(R.id.destination);
        source1=(TextView)findViewById(R.id.source);
        phone_no1 = (TextView)findViewById(R.id.phone_no);
        Log.d("before","3");
        accept=(Button)findViewById(R.id.accept);
        Log.d("after","3");
           /** accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("b4","intent");
                    Intent intent=new Intent(MainActivity1.this,MainActivity2.class);
                    intent.putExtra("passenger_id", passenger_id);
                    startActivity(intent);
                }
            });**/
            reject=(Button)findViewById(R.id.reject);
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity1.this,MainActivity2.class);
                intent.putExtra("passenger_id", passenger_id);
                startActivity(intent);
                getStatus1();
            }
        });

        getStatus();
    }

    public void getStatus() {
      //  loading = ProgressDialog.show(this, "Fetching Driver Details", "Please wait..", false, false);
        RequestQueue rq = Volley.newRequestQueue(getBaseContext());
        String url = "http://192.168.137.103:8000/get_passenger";
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
                params.put("id",passenger_id );
                return params;
            }
        };
        rq.add(sr);
    }

    private void showJSON(String json) throws JSONException {

        JSONObject reader = new JSONObject(json);
        // String status = reader.getString("status");
       // loading.dismiss();
        name = reader.getString("name");
        timestamp = reader.getString("timestamp");
       // destination = reader.getString("destination");
       // source = reader.getString("source");
        //lat_source=reader.getString("source_lat");
        //long_source=reader.getString("source_long");

        phone_no = reader.getString("phone_no");
        name1.setText(name);
        phone_no1.setText(phone_no);
        timestamp1.setText(timestamp);
       // source1.setText(source);
        //destination1.setText(destination);





    }
    public void getStatus1() {
        loading = ProgressDialog.show(this, "Fetching Driver Details", "Please wait..", false, false);
        RequestQueue rq = Volley.newRequestQueue(getBaseContext());
        String url = "http://192.168.137.103:8000/driver_response";
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
                params.put("id",value );
                params.put("request_id",passenger_id);
                return params;
            }
        };
        rq.add(sr);
    }

}
