package com.yd.ecabinet.rfid;

import com.clou.uhf.G3Lib.CLReader;
import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.clou.uhf.G3Lib.Enumeration.eGPO;
import com.clou.uhf.G3Lib.Enumeration.eGPOState;
import com.clou.uhf.G3Lib.Tag6C;
import com.yd.ecabinet.util.LoggerUtils;
import com.yd.ecabinet.util.ThreadUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;

import static com.clou.uhf.G3Lib.Enumeration.eReadType.Inventory;
import static com.yd.ecabinet.config.Config.*;

@Component
public class DefaultRfidOperator implements RfidOperator {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private Boolean reading = false;

    @Override
    public synchronized void connect(IAsynchronousMessage callback) {
        boolean connected = CLReader.CreateTcpConn(RFID_SN, callback);

        while (!connected) {
            logger.error("RFID连接失败");

            ThreadUtils.await(TRY_INTERVAL);

            connected = CLReader.CreateTcpConn(RFID_SN, callback);
        }

        logger.info("RFID连接成功");

        CLReader.Stop(RFID_SN);
    }

    @Override
    public synchronized boolean isConnect() {
        try {
            String status = CLReader._Config.GetReaderInformation(RFID_SN);
            logger.debug("连接信息:{}", status);

            return StringUtils.hasText(status) && status.startsWith("V");
        } catch (InterruptedException e) {
            logger.debug("读取连接信息失败", e);
            return false;
        }
    }

    @Override
    public synchronized void disconnect() {
        CLReader.CloseConn(RFID_SN);
    }

    @Override
    public synchronized void disconnectAll() {
        CLReader.CloseAllConnect();
    }

    @Override
    public synchronized void startRead() {
        this.stopRead();

        int read = Tag6C.GetEPC_TID(RFID_SN, RFID_ANT, Inventory);

        logger.info("正在开启读标签...");

        while (read != 0) {
            logger.error("开启读标签失败,错误码:{}", read);
            ThreadUtils.await(TRY_INTERVAL);

            logger.error("正在重试开启读标签...");
            read = Tag6C.GetEPC_TID(RFID_SN, RFID_ANT, Inventory);
        }

        logger.info("开启读标签成功");

        reading = true;
    }

    @Override
    public synchronized void stopRead() {
        if (!reading) {
            return;
        }

        logger.info("停止读标签...");

        try {
            int stop = CLReader._Config.Stop(RFID_SN);
            while (stop != 0) {
                stop = CLReader._Config.Stop(RFID_SN);
                logger.error("停止读标签失败,正在重试...");
            }

            logger.error("停止读标签成功");

            reading = false;
        } catch (InterruptedException e) {
            logger.error("停止读标签失败", e);
        }
    }

    @Override
    public synchronized boolean isOpen() {
        try {
            String state = CLReader._Config.GetReaderGPIState(RFID_SN);
            return StringUtils.hasText(state) && state.contains("High");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized void openDoor() {
        logger.info("正在开门...");

        try {
            this.operateDoor(eGPOState._High, RFID_TIMES);
        } catch (Exception e) {
            logger.error("开门出错", e);
        }

        ThreadUtils.await(RFID_WAIT);

        this.closeDoor();
    }

    @Override
    public synchronized void closeDoor() {
        logger.info("正在重置电平以监测关门信号...");

        try {
            this.operateDoor(eGPOState.Low, RFID_TIMES);
        } catch (Exception e) {
            logger.error("重置电平出错", e);
        }
    }

    private synchronized void operateDoor(eGPOState state, int maxTimes) throws Exception {
        HashMap<eGPO, eGPOState> map = new HashMap<>();
        map.put(eGPO._1, state);

        int times = 0;
        boolean flag = CLReader._Config.SetReaderGPOState(RFID_SN, map) == 0;
        while (!flag && times < maxTimes) {
            logger.error("操作失败,正在重试...");

            ThreadUtils.await(TRY_INTERVAL);

            flag = CLReader._Config.SetReaderGPOState(RFID_SN, map) == 0;
            times++;
        }

        if (flag) {
            logger.info("操作成功");
        } else {
            logger.error("操作失败");
        }
    }
}
