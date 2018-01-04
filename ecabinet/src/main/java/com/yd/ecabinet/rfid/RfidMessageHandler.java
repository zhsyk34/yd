package com.yd.ecabinet.rfid;

import com.clou.uhf.G3Lib.Protocol.Tag_Model;
import com.yd.ecabinet.tcp.TcpServer;
import com.yd.rfid.RfidMessageAdapter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.yd.ecabinet.util.LoggerUtils.getLogger;

@Component
public class RfidMessageHandler extends RfidMessageAdapter {

    private final Logger logger = getLogger(getClass());

    @Autowired
    private TcpServer tcpServer;

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

//    //TODO: test
//    private static void reportOrders() throws JsonProcessingException {
//        Map<String, Object> map = new HashMap<>();
//        map.put("device_type", "cabinet");
//        map.put("shop_code", StoreConfig.NUMBER);
//
//        Map<String, String> info = new HashMap<>();
//        info.put("6928804014649", "2");
//        info.put("6917878027333", "3");
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(info);
//
////        map.put("spec_code", "\"" + json + "\"");
//        map.put("spec_code", json);
//
//        System.out.println(map);
//
//        System.err.println(StoreConfig.SERVER);
//        System.out.println(HttpUtils.postForm(StoreConfig.SERVER, map));
//    }
//
//    public static void main(String[] args) throws JsonProcessingException {
//        reportOrders();
//    }

}
