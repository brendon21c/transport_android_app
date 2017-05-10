package com.brendon.trransport_android_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    Button mLoginButton;
    EditText mLoginEntry;

    String loginResult;

    int mDriverID;

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


                //int driver_id = Integer.parseInt(mLoginEntry.getText().toString());

                mDriverID = Integer.parseInt(mLoginEntry.getText().toString());


                try {

                    String url_string = "http://10.0.2.2:5000/api/driver_login/?driverid=" + mDriverID;

                    LoginDriver login = new LoginDriver();
                    login.execute(url_string);


                } catch (Exception e) {

                    Log.e("error", "Error logging in Driver", e);

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


            if (driving.equalsIgnoreCase("True")) {

                Intent intent = new Intent(MainActivity.this, RouteActivity.class);
                intent.putExtra("driverID", mDriverID);
                startActivityForResult(intent, DRIVER_LOGIN_CODE);

            }

            // Currently the program will only allow "working" drivers to log in.
            else {

                Toast.makeText(MainActivity.this, mDriverID + " is not a valid driver number.", Toast.LENGTH_LONG).show();

            }

        }
    }


}
