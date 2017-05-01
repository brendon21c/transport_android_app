package com.brendon.trransport_android_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    Button mLoginButton;
    EditText mLoginEntry;

    String loginResult;

    private static final int DRIVER_LOGIN_CODE = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mLoginButton = (Button) findViewById(R.id.driver_login_button);
        mLoginEntry = (EditText) findViewById(R.id.driver_id_entry);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int driver_id = Integer.parseInt(mLoginEntry.getText().toString());

                try {

                    String url_string = "http://10.0.2.2:5000/api/driver_login/?driverid=" + driver_id;

                    LoginDriver login = new LoginDriver();
                    login.execute(url_string);

                    String result = "driving";
                    System.out.println(result);

                    if (result.equals(loginResult)) {

                        Intent intent = new Intent(MainActivity.this, RouteView.class);
                        intent.putExtra("driverID", driver_id);
                        //intent.putExtra("driverID", driver_id);
                        startActivityForResult(intent, DRIVER_LOGIN_CODE);

                    }

                    else {

                        System.out.println(driver_id + " is not working.");

                    }


                } catch (Exception e) {

                    e.printStackTrace();
                    System.out.println("error: " + e);
                }


            }
        });

    }

    /*
    This class will call the API and see if the driver ID number matches someone who is working.
    If they are the next page will load.
     */
    private class LoginDriver extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream responseStream = connection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseStream));

                StringBuilder builder = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {

                    builder.append(line);

                }

                String responseString = builder.toString();

                return responseString;




            } catch (Exception e) {

                Log.e("error", "Error connecting to API", e);
                e.printStackTrace();
                System.out.println("error: " + e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(String driving) {

            loginResult = driving;

        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
    }
}
