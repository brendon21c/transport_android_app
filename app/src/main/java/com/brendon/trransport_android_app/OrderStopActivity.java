package com.brendon.trransport_android_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.brendon.trransport_android_app.models.Delivery;
import com.brendon.trransport_android_app.models.Pickup;
import com.brendon.trransport_android_app.models.RouteStop;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OrderStopActivity extends AppCompatActivity {


    TextView mCustomer;
    TextView mCity;
    TextView mAddress;
    TextView mZipCode;
    TextView mAction;
    Button mComplete;

    int driverID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_stop);

        mCustomer = (TextView) findViewById(R.id.customer);
        mCity = (TextView) findViewById(R.id.city);
        mAddress = (TextView) findViewById(R.id.address);
        mZipCode = (TextView) findViewById(R.id.zip_code);
        mAction = (TextView) findViewById(R.id.action);
        mComplete = (Button) findViewById(R.id.complete_stop_button);


        Intent intent = getIntent();

        final RouteStop stop = intent.getExtras().getParcelable("stop");

        driverID = intent.getExtras().getInt("driver");


        /*
         TODO would like to eventually code this so the program doesn't need to cast the information as a Pickup or Delivery,
          TODO but an do it on its own.
          */

        if (stop instanceof Pickup) {

            mCustomer.setText(((Pickup) stop).getCustomer());
            mAddress.setText(((Pickup) stop).getAddress());
            mCity.setText(((Pickup) stop).getCity());
            mZipCode.setText(((Pickup) stop).getZip_code());
            mAction.setText(((Pickup) stop).getAction());

        }

        else if (stop instanceof Delivery) {


            mCustomer.setText(((Delivery) stop).getCustomer());
            mAddress.setText(((Delivery) stop).getAddress());
            mCity.setText(((Delivery) stop).getCity());
            mZipCode.setText(((Delivery) stop).getZip_code());
            mAction.setText(((Delivery) stop).getAction());

        }

        // Web Service API calls need to know if this is a pickup or delivery order. That's why there is an IF/ELSE statement.
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("click");


                if (stop instanceof Pickup) {


                    String orderNumber = ((Pickup) stop).getOrderNumber();

                    String url = "http://10.0.2.2:5000/api/pickup/?driverid=%d&ordernum=%s";

                    url =  String.format(url, driverID, orderNumber); // Format String for url.

                    CompleteStop complete = new CompleteStop();
                    complete.execute(url);

                } else if (stop instanceof Delivery) {

                    String orderNumber = ((Delivery) stop).getOrderNumber();

                    String url = "http://10.0.2.2:5000/api/delivery/?driverid=%d&ordernum=%s";

                    url =  String.format(url, driverID, orderNumber); // Format String for url.

                    CompleteStop complete = new CompleteStop();
                    complete.execute(url);

                }

            }
        });



    }

    // Make the URL call to the web service and display message to User on result.
    private class CompleteStop extends AsyncTask<String, Void, String> {


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

                String result = builder.toString();

                return result;


            } catch (Exception e) {

                // Print error if failed
                Log.e("error", "Error connecting to API", e);
                return null;
            }




        }


        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(OrderStopActivity.this, "Result is: " + result, Toast.LENGTH_LONG).show();

        }
    }




}
