package com.yd.ecabinet.pi.test;

import com.pi4j.io.gpio.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ControlLed {
    private static GpioController gpio = GpioFactory.getInstance();
    private static GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED", PinState.HIGH);

    public static void main(String s[]) {
        String urlStr = "";
        boolean current = getStatus(urlStr);

        setLedStatus(current);

        while (true) {
            try {
                if (getStatus(urlStr) != current) {
                    current = getStatus(urlStr);
                    setLedStatus(current);
                }
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean getStatus(String urlStr) {
        URL url;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));

            StringBuilder tempStr = new StringBuilder();
            while (rd.read() != -1) {
                tempStr.append(rd.readLine());
            }
            //System.out.print("--> 服务器上传感器的信息：");  
            String status = tempStr.substring(tempStr.lastIndexOf(":") + 1, tempStr.length() - 1);
            //System.out.println(status);  
            return status.equals("1");
            //System.out.println(on);  
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void setLedStatus(boolean sta) {
        if (sta) {
            pin.low();
            //因为我的继电器是低电平有效  
            System.out.println("--> 更新GPIO的状态: 开");
        } else {
            pin.high();
            System.out.println("--> 更新GPIO的状态: 关");
        }
    }
}  