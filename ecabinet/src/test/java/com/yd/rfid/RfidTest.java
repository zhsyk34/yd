package com.yd.rfid;

import com.clou.uhf.G3Lib.CLReader;
import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.clou.uhf.G3Lib.Protocol.Tag_Model;
import com.clou.uhf.G3Lib.Tag6C;
import com.yd.ecabinet.util.ThreadUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.clou.uhf.G3Lib.Enumeration.eReadType.Inventory;
import static com.yd.ecabinet.config.Config.RFID_ANT;
import static com.yd.ecabinet.config.Config.RFID_SN;

public class RfidTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static IAsynchronousMessage callback = new IAsynchronousMessage() {

        @Override
        public void WriteDebugMsg(String s) {

        }

        @Override
        public void WriteLog(String s) {

        }

        @Override
        public void PortConnecting(String s) {

        }

        @Override
        public void PortClosing(String s) {

        }

        @Override
        public void OutPutTags(Tag_Model tag_model) {
            System.err.println("tag:" + tag_model._TID);
        }

        @Override
        public void OutPutTagsOver() {

        }

        @Override
        public void GPIControlMsg(int i, int i1, int i2) {

        }
    };

    @Test
    public void test() throws Exception {
        boolean connected = CLReader.CreateTcpConn(RFID_SN, callback);

        while (!connected) {
            logger.error("RFID连接失败");

            ThreadUtils.await(500);

            connected = CLReader.CreateTcpConn(RFID_SN, callback);
        }
        logger.info("RFID连接成功");

        CLReader.Stop(RFID_SN);

        //TODO:test
        while (true) {
            logger.error("正在开启读取...");
            int start = Tag6C.GetEPC_TID(RFID_SN, RFID_ANT, Inventory);
            while (start != 0) {
                start = Tag6C.GetEPC_TID(RFID_SN, RFID_ANT, Inventory);
                logger.error("读取失败,正在重试...");
            }
            logger.error("读取成功");

            ThreadUtils.await(500);

            logger.error("正在停止读取...");
            int stop = CLReader._Config.Stop(RFID_SN);
            while (stop != 0) {
                stop = CLReader._Config.Stop(RFID_SN);
                logger.error("停止读取失败,正在重试...");
            }
            logger.error("停止读取成功");
            ThreadUtils.await(3000);
        }
    }
}
