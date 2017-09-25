package com.yd.estore.rfid;

import com.clou.uhf.G3Lib.Protocol.Tag_Model;
import com.yd.estore.config.RfidConfig;
import com.yd.estore.tcp.TcpServerManager;
import com.yd.estore.util.JsonUtils;
import com.yd.estore.util.LoggerUtils;
import com.yd.estore.util.MessageFactory;
import com.yd.rfid.RfidMessageAdapter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RfidMessageHandler extends RfidMessageAdapter {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());
    private final TcpServerManager tcpServerManager;
    private long lastTime = System.currentTimeMillis();

    public RfidMessageHandler(@Autowired TcpServerManager tcpServerManager) {
        this.tcpServerManager = tcpServerManager;
    }

    @Override
    public void OutPutTags(Tag_Model tag) {
        logger.debug("读取标签:{}", tag._TID);
        if (StringUtils.hasText(tag._TID)) {
            this.send(MessageFactory.tag(tag._TID));
        }
    }

    @Override
    public void GPIControlMsg(int gpiIndex, int gpiState, int startOrStop) {
        if (gpiState == 1) {
            logger.debug("检测到开门事件");
            long current = System.currentTimeMillis();
            if (current - lastTime > RfidConfig.RFID_CLEAN) {
                logger.info("通知清空购物车");
                this.send(MessageFactory.clean());
                this.lastTime = current;
            }
        }
    }

    private void send(Object o) {
        String msg = JsonUtils.toJson(o);
        if (tcpServerManager.hasClient()) {
            tcpServerManager.broadcast(msg);
        } else {
            logger.error("当前客户端已断开,忽略本次发送");
        }
    }
}
