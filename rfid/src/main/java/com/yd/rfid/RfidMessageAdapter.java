package com.yd.rfid;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;

public abstract class RfidMessageAdapter implements IAsynchronousMessage {

    /**
     * @param s 打印API内部过程调试信息
     */
    @Override
    public void WriteDebugMsg(String s) {
    }

    /**
     * @param s API记录日志回调（暂时未开放）
     */
    @Override
    public void WriteLog(String s) {
    }

    /**
     * @param s TCP服务器模式下的客户端连接回调
     */
    @Override
    public void PortConnecting(String s) {
    }

    /**
     * @param s 当设备连接断开时API会回调连接ID表明当前连接ID的设备已经断开连接
     */
    @Override
    public void PortClosing(String s) {
    }

    /**
     * 无论是单读取、循环读。在最后一张标签上传之后会有一个同步结束信号上传表明当前读取标签动作结束
     */
    @Override
    public void OutPutTagsOver() {
    }

}
