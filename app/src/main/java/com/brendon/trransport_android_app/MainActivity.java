package com.brendon.trransport_android_app;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    Button mLoginButton;
    EditText mLoginEntry;



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

                    String url_string = "http://10.0.2.2:5000/api/routes/?driverid=" + driver_id;

                    GetDriverRoute tempTask = new GetDriverRoute();

                    tempTask.execute(url_string);

                    //new GetDriverRoute().execute(url_string);


                } catch (Exception e) {

                    e.printStackTrace();
                    System.out.println("error: " + e);
                }


            }
        });

    }

    private class GetDriverRoute extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... urls) {

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

                Gson gson = new GsonBuilder().create();




            } catch (Exception e) {

                Log.e("error", "Error connecting to API", e);
                e.printStackTrace();
                System.out.println("error: " + e);

            }


            return null;


        }
    }
}
