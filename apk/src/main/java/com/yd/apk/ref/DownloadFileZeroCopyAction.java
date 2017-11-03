//package com.yd.apk.ref;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.nio.channels.Channels;
//import java.nio.channels.FileChannel;
//import java.nio.channels.WritableByteChannel;
//
//@WebServlet("/zcdown")
//
//public class DownloadFileZeroCopyAction extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//
//    private final Logger log = LoggerFactory
//            .getLogger(DownloadFileZeroCopyAction.class);
//
//    protected void doGet(HttpServletRequest request,
//
//                         HttpServletResponse response) throws ServletException, IOException {
//
//// 传递路径用于下载
//
//        String path = request.getParameter("path");
//
//        long start = System.currentTimeMillis();
//
//        downloadFile(path, null, response);
//
//        log.info("use time : " + (System.currentTimeMillis() - start));
//
//    }
//
//    private static void downloadFile(String filePath, String fileName,
//
//                                     HttpServletResponse response) {
//
//        if (filePath == null || filePath.trim().equals("") || response == null) {
//
//            output(response, "请求参数有误！");
//
//            return;
//
//        }
//
//        File file = new File(filePath);
//
//        if (!file.exists() || file.isDirectory()) {
//
//            output(response, "您请求的文件不存在或者路径有误！");
//
//            return;
//
//        }
//
//        if (fileName == null || fileName.trim().equals("")) {
//
//            fileName = FilenameUtils.getName(filePath);
//
//        }
//
//        try {
//
//// 对下载的文件名称进行编码，避免出现中文乱码问题
//
//            response.setHeader("Content-disposition", "attachment; filename="
//
//                    + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
//
//            FileChannel fileChannel = new FileInputStream(file).getChannel();
//
//            OutputStream out = response.getOutputStream();
//
//            WritableByteChannel outChannel = Channels.newChannel(out);
//
//            fileChannel.transferTo(0, file.length(), outChannel);
//
//            out.flush();
//
//            out.close();
//
//        } catch (FileNotFoundException notFound) {
//
//            output(response, "您所请求的文件不存在！");
//
//        } catch (IOException ioe) {
//
//            ioe.printStackTrace();
//
//            output(response, "您所请求的文件出现异常！");
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            output(response, "您所请求的文件出现异常！");
//
//        }
//
//    }
//
//    private static void output(HttpServletResponse response, String message) {
//
//        try {
//
//            response.setContentType("text/html; charset=UTF-8");
//
//            Writer out = response.getWriter();
//
//            out.write(message);
//
//            out.flush();
//
//            out.close();
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//    }
//
//}