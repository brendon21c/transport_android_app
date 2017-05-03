package com.brendon.trransport_android_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.brendon.trransport_android_app.models.Delivery;
import com.brendon.trransport_android_app.models.Order_Gson;
import com.brendon.trransport_android_app.models.Pickup;
import com.brendon.trransport_android_app.models.RouteStop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
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



    private class GetDriverRoute extends AsyncTask<String, Void, RouteStop[]> {

        @Override
        protected RouteStop[] doInBackground(String... urls) {

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

                RouteStop[] routeStop = createRouteStops(pickup,deliveries);

                return routeStop;



            } catch (Exception e) {

                Log.e("error", "Error connecting to API", e);
                e.printStackTrace();
                System.out.println("error: " + e);

            }


            return null;


        }

        @Override
        protected void onPostExecute(RouteStop[] routeStops) {

            createListOfRoutes(routeStops);

        }
    }


    // Create a array of both Pickup and Delivery stops.
    private RouteStop[] createRouteStops(Pickup[] pickups, Delivery[] deliveries) {


        // Set a length.
        RouteStop[] stops = new RouteStop[pickups.length + deliveries.length];

        int index = 0;

        for (Pickup p : pickups) {

            stops[index++] = p;

        }

        for (Delivery d : deliveries) {

            stops[index++] = d;

        }

        Arrays.sort(stops, new StopsSorter());

        return stops;

    }


    private void createListOfRoutes(RouteStop[] routeStops) {

        RouteListAdapter adapter = new RouteListAdapter(this, R.layout.route_stop_list_item);
        adapter.addAll(routeStops);
        mRouteList.setAdapter(adapter);

        //todo add listener for clicking on adapter
    }


}
