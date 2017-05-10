package com.brendon.trransport_android_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class RouteActivity extends AppCompatActivity {

    TextView mDriverInstructions;
    Button mRefresh;
    ListView mRouteList;


    HashMap<String,String> mRouteHash;

    int mDriverID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_view);


        mDriverInstructions = (TextView) findViewById(R.id.route_instructions);
        mRefresh = (Button) findViewById(R.id.refresh_list);
        mRouteList = (ListView) findViewById(R.id.route_list_view);

        mRouteHash = new HashMap<String, String>();

        Intent intent = getIntent();

        int driver_id = intent.getIntExtra("driverID", 0);

        mDriverID = driver_id; // Assigning to Global variable so that it can be passed to OrderStopActivity.

        String url_string = "http://10.0.2.2:5000/api/routes/?driverid=" + String.valueOf(driver_id);

        GetDriverRoute temptask = new GetDriverRoute();

        temptask.execute(url_string);

        // Pauses program to update list... Hopefully.
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onPause();

            }
        });


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

                Pickup[] pickup = orders.getPickup();
                Delivery[] deliveries = orders.getDelivery();

                RouteStop[] routeStop = createRouteStops(pickup,deliveries);

                return routeStop;



            } catch (Exception e) {

                Log.e("error", "Error connecting to API", e);


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

    // Called on postExecute when the program makes a api request. This sets the list adapter for the current jobs.
    private void createListOfRoutes(RouteStop[] routeStops) {

        final RouteListAdapter adapter = new RouteListAdapter(this, R.layout.route_stop_list_item);
        adapter.addAll(routeStops);
        mRouteList.setAdapter(adapter);


        // Starts the Activity for completing a stop.
        mRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Collect "Stop" information and pass to order processing screen.
                RouteStop selection = adapter.getItem(position);

                Intent intent = new Intent(RouteActivity.this, OrderStopActivity.class);
                intent.putExtra("stop", selection);
                intent.putExtra("driver", mDriverID);
                startActivityForResult(intent,RESULT_OK);

            }
        });



    }


    // Download and update Listview when Activity restarts.
    @Override
    protected void onResume() {
        super.onResume();

        String url_string = "http://10.0.2.2:5000/api/routes/?driverid=" + String.valueOf(mDriverID);

        GetDriverRoute temptask = new GetDriverRoute();

        temptask.execute(url_string);


    }

    //not sure if this the best way to do this, but this is allowing the user to click a button and refresh the list on demand.
    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("paused");

        onResume();
    }
}


