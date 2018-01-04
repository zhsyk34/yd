package com.yd.ecabinet.rfid;

import com.clou.uhf.G3Lib.Protocol.Tag_Model;
import com.yd.ecabinet.tcp.TcpServer;
import com.yd.rfid.RfidMessageAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RfidMessageHandler extends RfidMessageAdapter {

    private final TcpServer tcpServer;

    @Override
    public void OutPutTags(Tag_Model tag) {
    }

    /**
     * @param gpiIndex    GPI口的下标
     * @param gpiState    {0:低电平, 1:高电平}
     * @param startOrStop {0:触发开始, 1:触发停止}
     */
    @Override
    public void GPIControlMsg(int gpiIndex, int gpiState, int startOrStop) {
        logger.debug("电平状态:{}", gpiState);
        if (gpiState == 1) {
            logger.debug("检测到开门事件");
        } else {
            logger.debug("检测到关门事件,开始请求Python接口以提交订单...");
            tcpServer.send("0");//TODO
        }
    }

}
