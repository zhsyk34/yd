//package com.yd.apk.servlet;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.channels.Channels;
//import java.nio.channels.FileChannel;
//import java.util.Optional;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
//@WebServlet("/file/download")
//public class DownloadFileServlet extends HttpServlet {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//    private static final String DIR = "e:/upload/";
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String path = DIR + req.getParameter("path");
//        File file = new File(path);
//        if (!file.exists() || file.isDirectory()) {
//            logger.error("错误的请求路径...");
//            return;
//        }
//
//        ServletContext context = super.getServletContext();
//        String mimeType = Optional.ofNullable(context.getMimeType(path)).orElse("application/octet-stream");
//
//        resp.setContentType(mimeType);
//        resp.setContentLengthLong(file.length());
//
//        //forces download
//        String headerKey = "Content-Disposition";
//        String headerValue = String.format("attachment; filename=\"%s\"", new String(file.getName().getBytes(UTF_8), UTF_8));
//        resp.setHeader(headerKey, headerValue);
//
//        FileChannel input = new FileInputStream(file).getChannel();
//        OutputStream output = resp.getOutputStream();
//        input.transferTo(0, file.length(), Channels.newChannel(output));
//        output.flush();
//        output.close();
//    }
//}
