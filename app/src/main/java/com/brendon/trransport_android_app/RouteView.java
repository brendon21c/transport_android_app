package com.brendon.trransport_android_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class RouteView extends AppCompatActivity {

    TextView mDriverInstructions;
    Button mOrderDetailsButton;
    Button mGPSButton;
    ListView mRouteList;

    ArrayAdapter mRouteAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_view);


        mDriverInstructions = (TextView) findViewById(R.id.route_instructions);
        mOrderDetailsButton = (Button) findViewById(R.id.access_stop);
        mGPSButton = (Button) findViewById(R.id.gps_button);
        mRouteList = (ListView) findViewById(R.id.route_list_view);

        Intent intent = getIntent();

        int driver_id = intent.getIntExtra("driverID", 0);

        System.out.println(driver_id); //TODO Start here.



    }
}
