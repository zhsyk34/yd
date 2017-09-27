package com.yd.upload.rfid;

import com.clou.uhf.G3Lib.Protocol.Tag_Model;
import com.yd.rfid.RfidMessageAdapter;
import com.yd.upload.bean.Tag;
import com.yd.upload.config.StoreConfig;
import com.yd.upload.util.HttpUtils;
import com.yd.upload.util.LoggerUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

import static com.yd.rfid.executor.DaemonService.EXECUTOR;

@Component
public class RfidMessageHandler extends RfidMessageAdapter {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    @Override
    public void OutPutTags(Tag_Model tag) {
        if (StringUtils.hasText(tag._TID)) {
            EXECUTOR.submit(() -> {
                Map<String, Object> info = Tag.from(tag._TID);
                String result = HttpUtils.postForm(StoreConfig.SERVER, info);
                logger.info("已上传标签信息{},服务器响应:{}", info, result);
            });
        }
    }

    @Override
    public void GPIControlMsg(int gpiIndex, int gpiState, int startOrStop) {

    }

}
