package com.brendon.trransport_android_app;

import com.brendon.trransport_android_app.models.RouteStop;

import java.util.Comparator;

/**
 * Created by Brendon on 5/3/17.
 */

public class StopsSorter implements Comparator<com.brendon.trransport_android_app.models.RouteStop> {


    @Override
    public int compare(RouteStop o1, RouteStop o2) {

        return o1.getPriority() - o2.getPriority();

    }

}


