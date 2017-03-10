package com.example.pratibhaswami.myapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;


public class ExampleApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
         new ExampleNotificationOpenedHandler();


        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.WARN);

        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .autoPromptLocation(true)
                .init();
    }


    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        String id;
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
           JSONObject data = result.notification.payload.additionalData;
            //Log.d("data", String.valueOf(result.notification.payload.additionalData));

            try {
                id = data.getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.d("data",id);


            //data.notification.payload.additionalData.id;
            String customKey;



       /*   if (data != null) {
                customKey = data.optString("customkey", null);
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);
            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken)
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);*/



            // The following can be used to open an Activity of your choice.

             Intent intent = new Intent(ExampleApplication.this, MainActivity1.class);
            intent.putExtra("passenger_id", id);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(intent);

            // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
            //  if you are calling startActivity above.
         /*
            <application ...>
              <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
            </application>
         */

        }
    }
}