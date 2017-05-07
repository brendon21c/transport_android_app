package com.brendon.trransport_android_app.models;

import android.os.Parcelable;

/**
 * Created by Brendon on 5/3/17.
 */

public abstract class RouteStop implements Parcelable {

    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


}
