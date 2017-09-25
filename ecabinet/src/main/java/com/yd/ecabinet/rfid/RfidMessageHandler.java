package com.yd.ecabinet.rfid;

import com.clou.uhf.G3Lib.Protocol.Tag_Model;
import com.yd.ecabinet.rfid.order.TagService;
import com.yd.ecabinet.util.LoggerUtils;
import com.yd.rfid.RfidMessageAdapter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RfidMessageHandler extends RfidMessageAdapter {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final TagService tagService;

    @Autowired
    public RfidMessageHandler(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public void OutPutTags(Tag_Model tag) {
        if (StringUtils.hasText(tag._TID)) {
            tagService.statistics(tag._TID);
        }
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
            logger.debug("检测到关门事件,开始汇总剩余商品以统计订单...");

            tagService.process();
        }
    }

}
