package com.yd.rfid;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;

public interface RfidOperator {

    void connect(IAsynchronousMessage callback);

    boolean isConnect();

    void disconnect();

    void disconnectAll();

    void startRead();

    void stopRead();

    void openDoor();

    boolean isOpen();

    void closeDoor();
}
