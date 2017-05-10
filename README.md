# transport_android_app

## Overview

This is the android application that works in conjucntion with the transport_dispatcher application which can be found here: 
https://github.com/brendon21c/transport_dispatcher. All functionlaity of that program is still in effect and works in conjunction with this 
project. 

## Installation

The program is run from Andrpid Studio, no keys are needed as all information is stored by the Dispatcher program named above. No keys are needed, however the program does not currently run on your own personal device as the "dispatcher" program is not deployed, so please use
the virtual device.

## Functionality

This program is designed to be the first step in a larger application, but currently can do the following:

1. Drivers can log in using their driver ID. If there number is not in the database they will not be able to log in. 
2. Once they have logged in the system will display any jobs they currently have in the "route" screen and color code them based on Pickup,Delivery or Comopletetion.
3. They can click on a route and be taken to a "details" screen where they see stop information and can complete the stop and back up to the "route" screen.
4. From the route screen they can refresh the list via a button and see if they have any new routes. P.S. this will also happen when they back up from the "deatails" screen.

As mentioned before all data is entered into the system via the Tranportation site and managed from there. With one exception, currently the "routing logic" is based on a number scale which can only be changed manually by altering the database using either sqllite or a db interface such as DB Browser for SQLLite.

# Screens

## Log in Screen


