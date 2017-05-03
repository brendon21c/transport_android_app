package com.brendon.trransport_android_app.models;

/**
 * Created by Brendon on 4/26/17.
 */

public class Order_Gson {


    private com.brendon.trransport_android_app.models.Delivery[] Delivery;

    private Pickup[] Pickup;

    public Delivery[] getDelivery ()
    {
        return Delivery;
    }

    public void setDelivery (Delivery[] Delivery)
    {
        this.Delivery = Delivery;
    }

    public Pickup[] getPickup ()
    {
        return Pickup;
    }

    public void setPickup (Pickup[] Pickup)
    {
        this.Pickup = Pickup;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Delivery = "+Delivery+", Pickup = "+Pickup+"]";
    }


}
