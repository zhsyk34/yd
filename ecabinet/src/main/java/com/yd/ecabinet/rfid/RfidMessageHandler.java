package com.yd.ecabinet.rfid;

import com.clou.uhf.G3Lib.Protocol.Tag_Model;
import com.yd.rfid.RfidMessageAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class RfidMessageHandler extends RfidMessageAdapter {
    private final DoorStateHandler doorStateHandler;
    private final TagHandler tagHandler;

    @Override
    public void OutPutTags(Tag_Model tag_model) {
        String tid = tag_model._TID;
        if (StringUtils.hasText(tid)) {
            tagHandler.handle(tid);
        }
    }

    @Override
    public void GPIControlMsg(int gpiIndex, int gpiState, int startOrStop) {
        if (gpiState == 0) {
            doorStateHandler.onOpen();
        } else {
            doorStateHandler.onClose();
        }
    }
}
