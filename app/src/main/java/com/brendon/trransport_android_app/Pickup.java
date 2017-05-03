package com.brendon.trransport_android_app;

/**
 * Created by Brendon on 4/26/17.
 */

public class Pickup extends RouteStop {

    private String delivery_time;

    private String zip_code;

    private String address;

    private String OrderNumber;

    private String customer;

    private String pickup_time;

    private String city;

    private String action;

    public String getDelivery_time ()
    {
        return delivery_time;
    }

    public void setDelivery_time (String delivery_time)
    {
        this.delivery_time = delivery_time;
    }

    public String getZip_code ()
    {
        return zip_code;
    }

    public void setZip_code (String zip_code)
    {
        this.zip_code = zip_code;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getOrderNumber ()
    {
        return OrderNumber;
    }

    public void setOrderNumber (String OrderNumber)
    {
        this.OrderNumber = OrderNumber;
    }

    public String getCustomer ()
    {
        return customer;
    }

    public void setCustomer (String customer)
    {
        this.customer = customer;
    }

    public String getPickup_time ()
    {
        return pickup_time;
    }

    public void setPickup_time (String pickup_time)
    {
        this.pickup_time = pickup_time;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [delivery_time = "+delivery_time+", zip_code = "+zip_code+", address = "+address+", OrderNumber = "+OrderNumber+", customer = "+customer+", pickup_time = "+pickup_time+", city = "+city+"]";
    }


}
