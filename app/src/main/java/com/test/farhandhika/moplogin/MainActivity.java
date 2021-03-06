package com.test.farhandhika.moplogin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private DaoSession mDaoSession;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Reference to variables
        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        TextView textView = (TextView) findViewById(R.id.text);
        TextView textView2 = (TextView) findViewById(R.id.text2);
        TextView textView3 = (TextView) findViewById(R.id.text3);
        TextView textView4 = (TextView) findViewById(R.id.text4);
        TextView textView5 = (TextView) findViewById(R.id.text5);


        Token token = ((App)getApplication()).getDaoSession().getTokenDao().load(2L);


        if(token!= null) {

            textView.setText(token.getAccesstoken());
            textView2.setText(token.getExpiresin());
            textView3.setText(token.getTokentype());
            textView4.setText(token.getScope());
            textView5.setText(token.getRefreshtoken());
        }



    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {

        // Get text from email and password field
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(username,password);

    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {


        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {

            final String username = etUsername.getText().toString();
            final String password = etPassword.getText().toString();

            try {

                URL url = new URL("https://dev.mopaps.xtend.net.my/oauth"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("username", username);
                postDataParams.put("password", password);
                postDataParams.put("grant_type", "password");
                postDataParams.put("client_secret", "client1234");
                postDataParams.put("client_id", "mopaps-android");
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in =new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {
                        result.append(line);
                       break;

                    }

                    in.close();
                    return result.toString();

                }
                else {
                    return("unsuccessful");
                }
            }
            catch(Exception e){
                e.printStackTrace();
                return"exception";
                }

        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread



            pdLoading.dismiss();



            if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful"))
            {
                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
            }

            //Toast.makeText(getApplicationContext(), "Success Adding data to SQLite!",
             //       Toast.LENGTH_LONG).show();

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        }



    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
}
}
