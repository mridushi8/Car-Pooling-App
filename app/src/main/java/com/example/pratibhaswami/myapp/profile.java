package com.example.pratibhaswami.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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

public class profile extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPref" ;
    public static final String Userid = "idKey";
    SharedPreferences sharedpreferences;

   /** TextView Name = (TextView) findViewById(R.id.nameo);
    TextView Address = (TextView) findViewById(R.id.tvNumber51);
    TextView Phone_no = (TextView) findViewById(R.id.tvNumber11);
    TextView Mail = (TextView) findViewById(R.id.tvNumber31);**/
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        /**FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });**/
    }

    public void getStatus() {
        loading = ProgressDialog.show(this, "Fetching Driver Details", "Please wait..", false, false);
        RequestQueue rq = Volley.newRequestQueue(getBaseContext());
        String url = "http://192.168.1.8:8000/api/get_passenger";
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
                String value = sharedpreferences.getString(Userid, "");
                params.put("id",value);
                return params;
            }
        };
        rq.add(sr);
    }

    private void showJSON(String json) throws JSONException {

        JSONObject reader = new JSONObject(json);
        loading.dismiss();
        String name = reader.getString("name");
        String address = reader.getString("home_add");
        String phone_no = reader.getString("phoneno");
        String email = reader.getString("email");
        //Name.setText(name);
        //Phone_no.setText(phone_no);
        //Mail.setText(email);
        //Address.setText(address);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
