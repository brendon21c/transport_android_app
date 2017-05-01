package com.brendon.trransport_android_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class RouteView extends AppCompatActivity {

    TextView mDriverInstructions;
    Button mOrderDetailsButton;
    Button mGPSButton;
    ListView mRouteList;

    ArrayAdapter mRouteAdapter;

    HashMap<String,String> mRouteHash;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_view);


        mDriverInstructions = (TextView) findViewById(R.id.route_instructions);
        mOrderDetailsButton = (Button) findViewById(R.id.access_stop);
        mGPSButton = (Button) findViewById(R.id.gps_button);
        mRouteList = (ListView) findViewById(R.id.route_list_view);

        mRouteHash = new HashMap<String, String>();

        Intent intent = getIntent();

        int driver_id = intent.getIntExtra("driverID", 0);

        System.out.println(driver_id);

        String url_string = "http://10.0.2.2:5000/api/routes/?driverid=" + String.valueOf(driver_id);

        GetDriverRoute temptask = new GetDriverRoute();

        temptask.execute(url_string);



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

                Order_Gson orders = gson.fromJson(responseString, Order_Gson.class);

                //TODO I need to figure out how to use a GSON with a listadapter, I don't think a HashMap will work.
                Pickup[] pickup = orders.getPickup();
                Delivery[] deliveries = orders.getDelivery();


                for (int x = 0; x < pickup.length; x++) {

                    Pickup pickupItems = pickup[x];

                    mRouteHash.put("Order Number", pickupItems.getOrderNumber());
                    mRouteHash.put("Customer", pickupItems.getCustomer());
                    mRouteHash.put("Address", pickupItems.getAddress());
                    mRouteHash.put("City", pickupItems.getCity());
                    mRouteHash.put("Zip Code", pickupItems.getZip_code());
                    mRouteHash.put("Pickup Time", pickupItems.getPickup_time());



                }

                for (int y = 0; y < deliveries.length; y++) {

                    Delivery delItems = deliveries[y];
                    System.out.println(delItems.getOrderNumber());

                }




            } catch (Exception e) {

                Log.e("error", "Error connecting to API", e);
                e.printStackTrace();
                System.out.println("error: " + e);

            }


            return null;


        }
    }
}
