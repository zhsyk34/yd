package com.yd.ecabinet.pi;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

//TODO
//@Service
public class PiServiceImpl implements PiService {

    private static GpioController controller = GpioFactory.getInstance();

    private static GpioPinDigitalInput in = controller.provisionDigitalInputPin(RaspiPin.GPIO_02, "", PinPullResistance.PULL_DOWN);
    private static GpioPinDigitalOutput out = controller.provisionDigitalOutputPin(RaspiPin.GPIO_01, "", PinState.LOW);

    static {
        in.setShutdownOptions(true);
        out.setShutdownOptions(true);
        Runtime.getRuntime().addShutdownHook(new Thread(controller::shutdown));
    }

    @Override
    public void open() {
        out.setState(true);
    }

    @Override
    public void close() {
        out.setState(false);
    }

    @Override
    public void listener() {
        in.addListener(
                (GpioPinListenerDigital) event -> {
                    PinState state = event.getState();
                    System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + state);
                    switch (state) {
                        case HIGH:
                            break;
                        case LOW:
                            break;
                    }
                }
        );
    }
}
