package com.example.pratibhaswami.myapp;


import android.content.Context;
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
                { if(sharedpreferences.contains( "iden")|| sharedpreferences.contains("driv"))
                {
                    Intent i = new Intent(afterlogin.this, MapsActivity.class);
                    startActivity(i); }

                else {
                    Intent i = new Intent(afterlogin.this, MapsActivity.class);
                    startActivity(i);
                }
                }

                if (selectedrb.getText().equals("Driver"))
                {  if(sharedpreferences.contains("driv"))
                {
                    Intent i = new Intent(afterlogin.this, MapsActivity_driv.class);
                    startActivity(i); }

                else {
                    Intent i = new Intent(afterlogin.this, MapsActivity_driv.class);
                    startActivity(i);
                }
                }



// add it to the RequestQueue

            }

        });

    }
}
