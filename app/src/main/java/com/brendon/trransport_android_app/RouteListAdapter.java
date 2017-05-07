package com.brendon.trransport_android_app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

        TextView customer_label = (TextView) view.findViewById(R.id.customer_label);
        TextView customer = (TextView) view.findViewById(R.id.customer);
        TextView order_label = (TextView) view.findViewById(R.id.order_number_label);
        TextView order_number = (TextView) view.findViewById(R.id.order_number);
        TextView address_label = (TextView) view.findViewById(R.id.address_label);
        TextView address = (TextView) view.findViewById(R.id.address);

        if (stop instanceof Pickup) {

            if (((Pickup) stop).getAction().equalsIgnoreCase("complete")) {

                customer.setText(((Pickup) stop).getCustomer());
                order_number.setText(((Pickup) stop).getOrderNumber());
                address.setText(((Pickup) stop).getAddress());
                view.setBackgroundColor(Color.LTGRAY);


            } else {

                customer.setText(((Pickup) stop).getCustomer());
                order_number.setText(((Pickup) stop).getOrderNumber());
                address.setText(((Pickup) stop).getAddress());
                view.setBackgroundColor(Color.CYAN);


            }

        }

        else if (stop instanceof Delivery) {

            if (((Delivery) stop).getAction().equalsIgnoreCase("complete")) {

                customer.setText(((Delivery) stop).getCustomer());
                order_number.setText(((Delivery) stop).getOrderNumber());
                address.setText(((Delivery) stop).getAddress());
                view.setBackgroundColor(Color.LTGRAY);


            } else {

                customer.setText(((Delivery) stop).getCustomer());
                order_number.setText(((Delivery) stop).getOrderNumber());
                address.setText(((Delivery) stop).getAddress());

                view.setBackgroundColor(Color.GREEN);

            }

        }

        return view;

    }
}
