package com.example.pratibhaswami.myapp;

import android.util.Log;

/**
 * Created by pratibhaswami on 27/03/17.
 */

public class ServerUrl {
    public String serverUrl(String app){
        /**StringBuilder urlString = new StringBuilder();
        urlString.append("http://172.16.32.224:8000/");
        urlString.append(app);
        return urlString.toString();**/
        String url="";
        url="http://172.16.32.224:8000/"+app;
        Log.d("url",url);
        return url;
    }
}




