package com.example.arushi.login;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;


public class afterlogin extends AppCompatActivity {
    private RadioGroup travelas;
    private Button go;
    private RadioButton selectedrb;
    SharedPreferences sharedpreferences;
    String id;
    public static final String Name = "nameKey";
    public static final String Userid = "idKey";
    public static final String MyPREFERENCES = "MyPref" ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        travelas = (RadioGroup) findViewById(R.id.RBG);
        go = (Button) findViewById(R.id.go);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        go.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                id = sharedpreferences.getString(Userid, "");

                // get selected radio button from radioGroup
                int selectedId = travelas.getCheckedRadioButtonId();
                Log.d("my","select rb");

                // find the radiobutton by returned id
                selectedrb = (RadioButton) findViewById(selectedId);
                Log.d("my","selected rb");
                System.out.println(selectedrb.getText());
                if (selectedrb.getText().equals("Passenger"))
                {
                    Intent i = new Intent(afterlogin.this, MapsActivity.class);
                    startActivity(i);
                }

                     if (selectedrb.getText().equals("Driver"))
                     {    Log.d("my", "driver selected");
                         RequestQueue MyRequestQueue = Volley.newRequestQueue(getBaseContext());
                         String url= "http://192.168.1.9:8000/checkproof/";
                         StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
                             @Override
                             public void onResponse(String response) {
                                 Context context = getApplicationContext();
                                 Log.d("my", response);

                                 if (response.equals("\"1\"")) {
                                     Intent i = new Intent(afterlogin.this, MapActivity_driv.class);
                                     startActivity(i);

					 
                                 }
                                 else if (response.equals("\"0\""))
                                 {
                                     //documents for driving are not complete.
                                     //show pop up and redirect to register form initially
                                     AlertDialog alertDialog = new AlertDialog.Builder(
                                             afterlogin.this).create();
                                     alertDialog.setTitle("Incomplete Details");
                                     alertDialog.setMessage("Providing a Driving License and Car number is compulsory for a driver");
                                     alertDialog.setButton("Update Details", new DialogInterface.OnClickListener() {
                                         public void onClick(DialogInterface dialog, int which) {
                                             //redirect to profile
                                         }
                                     });
                                     alertDialog.show();

                                 }
                             } }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Context context = getApplicationContext();
                                 Toast.makeText(context, R.string.fail, LENGTH_LONG)
                                         .show();
                                 error.printStackTrace();
                             }
                         }){
                             protected Map<String, String> getParams(){
                                 Map<String, String> MyData = new HashMap<String, String>();
                                 MyData.put("id", id);
                                 //id will come from shared preferences

                                 return MyData;
                             };
                         };
                         MyRequestQueue.add(MyStringRequest);


                     }



// add it to the RequestQueue

            }

        });

    }
}
