package com.yd.rfid;

import com.clou.uhf.G3Lib.CLReader;
import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.clou.uhf.G3Lib.Enumeration.eGPO;
import com.clou.uhf.G3Lib.Enumeration.eGPOState;
import com.clou.uhf.G3Lib.Tag6C;
import com.yd.rfid.util.ThreadUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import java.util.HashMap;

import static com.clou.uhf.G3Lib.Enumeration.eReadType.Inventory;

@RequiredArgsConstructor(staticName = "instance")
public class DefaultRfidOperator implements RfidOperator {

    private final Logger logger;

    private final String id;
    private final int ant;

    /**
     * 操作间隔时间
     */
    private final int interval;
    /**
     * 重试次数
     */
    private final int retry;

    @Override
    public void connect(IAsynchronousMessage callback) {
        boolean connected = CLReader.CreateTcpConn(id, callback);

        while (!connected) {
            logger.error("RFID连接失败");

            this.await();

            connected = CLReader.CreateTcpConn(id, callback);
        }

        logger.info("RFID连接成功");

        CLReader.Stop(id);
    }

    @Override
    public boolean isConnect() {
        try {
            String status = CLReader._Config.GetReaderInformation(id);

            logger.debug("连接信息:{}", status);

            return status != null && status.startsWith("V");
        } catch (InterruptedException e) {
            logger.error("读取连接信息失败", e);
            return false;
        }
    }

    @Override
    public void disconnect() {
        CLReader.CloseConn(id);
    }

    @Override
    public void disconnectAll() {
        CLReader.CloseAllConnect();
    }

    @Override
    public void startRead() {
        this.stopRead();

        int read = Tag6C.GetEPC_TID(id, ant, Inventory);

        logger.info("正在开启读标签...");

        while (read != 0) {
            logger.error("开启读标签失败,错误码:{}", read);
            this.await();

            logger.error("正在重试开启读标签...");
            read = Tag6C.GetEPC_TID(id, ant, Inventory);
        }

        logger.info("开启读标签成功");
    }

    @Override
    public void stopRead() {
        logger.info("停止读标签...");

        try {
            int stop = CLReader._Config.Stop(id);
            while (stop != 0) {
                stop = CLReader._Config.Stop(id);
                logger.error("停止读标签失败,正在重试...");
            }

            logger.error("停止读标签成功");

        } catch (InterruptedException e) {
            logger.error("停止读标签失败", e);
        }
    }

    @Override
    public boolean isOpen() {
        try {
            String state = CLReader._Config.GetReaderGPIState(id);
            return state != null && state.contains("High");
        } catch (InterruptedException e) {
            logger.error("读取门(GPI)状态失败", e);
        }
        return false;
    }

    @Override
    public void openDoor() {
        logger.info("正在开门...");

        try {
            this.operateDoor(eGPOState._High, retry);
        } catch (Exception e) {
            logger.error("开门出错", e);
        }
    }

    @Override
    public void closeDoor() {
        logger.info("正在重置电平以监测关门信号...");

        try {
            this.operateDoor(eGPOState.Low, retry);
        } catch (Exception e) {
            logger.error("重置电平出错", e);
        }
    }

    private void operateDoor(eGPOState state, int maxTimes) throws Exception {
        HashMap<eGPO, eGPOState> map = new HashMap<>();
        map.put(eGPO._1, state);

        int times = 0;
        boolean flag = CLReader._Config.SetReaderGPOState(id, map) == 0;
        while (!flag && times < maxTimes) {
            logger.error("操作失败,正在重试...");

            this.await();

            flag = CLReader._Config.SetReaderGPOState(id, map) == 0;
            times++;
        }

        if (flag) {
            logger.info("操作成功");
        } else {
            logger.error("操作失败");
        }
    }

    private void await() {
        ThreadUtils.await(interval);
    }
}
