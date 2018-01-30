package com.yd.ecabinet.server.script;

import com.yd.ecabinet.rfid.TagHandler;
import org.springframework.stereotype.Service;

@Service
public class NonTagHandler implements TagHandler {
    @Override
    public void handle(String tid) {

    }
}
