package com.example.pratibhaswami.myapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by ARATI on 2/1/2017.
 */

public class Details extends AppCompatActivity {
	private GoogleApiClient client2;
	String name, phone, value;
	TextView textViewName, textViewPhone,textViewCarNo;
	Button accept, cancelTrip,startjourney;
	public static final String Userid = "idKey";
	public static final String MyPREFERENCES = "MyPref" ;
	SharedPreferences sharedpreferences;
	ProgressDialog loading;
    Handler handler = new Handler();
	String flag="0";
	String carno;


    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);


        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
		textViewCarNo = (TextView) findViewById(R.id.textViewCarno);
		accept = (Button) findViewById(R.id.accept);
		startjourney = (Button) findViewById(R.id.startButton);
		startjourney.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i=new Intent(Details.this,Trip.class);
				startActivity(i);
			}
		});
		accept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				flag="1";
				getStatus1();
			}
		});
		cancelTrip = (Button) findViewById(R.id.cancelButton);
		cancelTrip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				flag="-1";
				getStatus1();
				cancelTrip(view);
			}
		});
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		value = sharedpreferences.getString(Userid, "");
		loading = ProgressDialog.show(this, "Fetching Driver Details", "Please wait..", false, false);
		getStatus();
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	@Override
	public void onBackPressed() { }

	



public void getStatus() {
	//loading = ProgressDialog.show(this, "Fetching Driver Details", "Please wait..", false, false);
	RequestQueue rq = Volley.newRequestQueue(getBaseContext());
	String url = Constants.url + "status";
	StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() { 
			@RequiresApi(api = Build.VERSION_CODES.KITKAT)
			@Override
			public void onResponse(String rsp) { 
				showJSON(rsp);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		}) 
		{ 
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("id", value);
				return params;
			}
		};
	rq.add(sr);
}

	public void getStatus1() {
		//loading = ProgressDialog.show(this, "Fetching Driver Details", "Please wait..", false, false);
		RequestQueue rq = Volley.newRequestQueue(getBaseContext());
		String url = Constants.url + "accept_driver";
		StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
			@RequiresApi(api = Build.VERSION_CODES.KITKAT)
			@Override
			public void onResponse(String rsp) {
				//showJSON(rsp);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		})
		{
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("request_id", value);
				params.put("flag", flag);
				return params;
			}
		};
		rq.add(sr);
	}


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
private void showJSON(String json) {
	//loading.dismiss();
	try {
		JSONObject reader = new JSONObject(json);
		String status = reader.getString("status");
            	if(Objects.equals(status, "202")) {
					getDriver();

           	}
           	else{ 
			handler.postDelayed(new Runnable() { 
				@Override
				public void run() {
					getStatus();
                    		}
               		}, 3000);
            	}
	} catch (JSONException e) {
		e.printStackTrace();
	}
}
    
public Action getIndexApiAction() {
		Thing object = new Thing.Builder().setName("Details Page").setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]")).build();
		return new Action.Builder(Action.TYPE_VIEW).setObject(object).setActionStatus(Action.STATUS_TYPE_COMPLETED).build();
}

@Override
public void onStart() {
	super.onStart();
	client2.connect();
	AppIndex.AppIndexApi.start(client2, getIndexApiAction());
}

@Override
public void onStop() {
	super.onStop();
	AppIndex.AppIndexApi.end(client2, getIndexApiAction());
	client2.disconnect();
}

/*public void startJourney(View view){
	Intent i = new Intent(Details.this, Trip.class);
	startActivity(i);
}*/

public void cancelTrip(View view){
	RequestQueue rq = Volley.newRequestQueue(getBaseContext());
        String url = Constants.url + "complete";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent i = new Intent(Details.this, MapsActivity.class);
                startActivity(i);
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
                params.put("id", value);
                return params;
            }
        };
        rq.add(sr);
}

	public void getDriver(){
		RequestQueue rq = Volley.newRequestQueue(getBaseContext());
		String url = Constants.url + "get_driver";
		StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try{
					JSONObject reader = new JSONObject(response);
					loading.dismiss();
					name = reader.getString("name");
					phone = reader.getString("phone");
					carno = reader.getString("carno");

				} catch (JSONException e) {
					e.printStackTrace();
				}
				textViewName.setText(name);
				textViewPhone.setText(phone);
				textViewCarNo.setText(carno);

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
				params.put("id", value);
				return params;
			}
		};
		rq.add(sr);
	}


}



