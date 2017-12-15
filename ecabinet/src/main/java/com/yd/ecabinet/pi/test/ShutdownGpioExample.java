package com.yd.ecabinet.pi.test;

import com.pi4j.io.gpio.*;

public class ShutdownGpioExample {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("<--Pi4J--> GPIO Shutdown Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.HIGH);

        // configure the pin shutdown behavior; these settings will be
        // automatically applied to the pin when the application is terminated
        pin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

        System.out.println("--> GPIO state should be: ON");
        System.out.println("    This program will automatically terminate in 10 seconds,");
        System.out.println("    or you can use the CTRL-C keystroke to terminate at any time.");
        System.out.println("    When the program terminates, the GPIO state should be shutdown and set to: OFF");

        // wait 10 seconds
        Thread.sleep(10000);

        System.out.println(" .. shutting down now ...");

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();

        System.out.println("Exiting ShutdownGpioExample");
    }
}