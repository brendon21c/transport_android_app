package com.brendon.trransport_android_app;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brendon.trransport_android_app.models.Delivery;
import com.brendon.trransport_android_app.models.Pickup;
import com.brendon.trransport_android_app.models.RouteStop;

/**
 * Created by Brendon on 5/3/17.
 */

public class RouteListAdapter extends ArrayAdapter<RouteStop> {



    Context mContext;


    public RouteListAdapter(Context context, int resource) {

        super(context, resource);

        this.mContext = context;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View view = convertView;

        if (view == null) {

            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            view = inflater.inflate(R.layout.route_stop_list_item, parent, false);

        }

        RouteStop stop = getItem(position);

        TextView deliveryOrPickup = (TextView) view.findViewById(R.id.delivery_or_pickup);
        TextView address = (TextView) view.findViewById(R.id.address);

        if (stop instanceof Pickup) {

            deliveryOrPickup.setText("Pickup");
            address.setText(((Pickup) stop).getAddress());
        }

        else if (stop instanceof Delivery) {

            deliveryOrPickup.setText("Delivery");
            address.setText(((Delivery) stop).getAddress());

        }

        return view;

    }
}
