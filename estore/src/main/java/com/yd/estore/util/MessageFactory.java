package com.yd.estore.util;

import com.yd.estore.config.Action;
import com.yd.estore.config.Command;
import com.yd.estore.rfid.TagMessage;
import com.yd.estore.udp.NetworkMessage;

public abstract class MessageFactory {

    public static Command clean() {
        return Command.of(Action.CLEAN_CART);
    }

    public static TagMessage tag(String tid) {
        return TagMessage.of(tid);
    }

    public static NetworkMessage networkMessage(String ip, int port) {
        return NetworkMessage.of(ip, port);
    }

}
