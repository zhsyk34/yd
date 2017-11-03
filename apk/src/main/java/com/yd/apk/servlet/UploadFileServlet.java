//package com.yd.apk.servlet;
//
//import com.yd.apk.util.UploadUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//@WebServlet("/file/upload")
//public class UploadFileServlet extends HttpServlet {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    private static final MultipartConfig config = UploadFileServlet.class.getAnnotation(MultipartConfig.class);
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
//
//        Part part = null;
//        try {
////            request.getParts();
//            part = request.getPart("file");
//        } catch (IllegalStateException e) {
//            // 上传文件超过注解所标注的maxRequestSize或maxFileSize值
//            if (config.maxRequestSize() == -1L) {
//                logger.info("the Part in the request is larger than maxFileSize");
//            } else if (config.maxFileSize() == -1L) {
//                logger.info("the request body is larger than maxRequestSize");
//            } else {
//                logger.info("the request body is larger than maxRequestSize, or any Part in the request is larger than maxFileSize");
//            }
//
//            logger.error("上传文件过大");
//            return;
//        } catch (IOException e) {
//            // 在接收数据时出现问题
//            logger.error("I/O error occurred during the retrieval of the requested Part");
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//
//        if (part == null) {
//            logger.error("上传文件出现异常");
//            return;
//        }
//
//        String fileName = UploadUtils.getFileName2(part);
//
//        logger.info("contentType : " + part.getContentType());
//        logger.info("fileName : " + fileName);
//        logger.info("fileSize : " + part.getSize());
//        logger.info("header names : ");
//        for (String headerName : part.getHeaderNames()) {
//            logger.info(headerName + " : " + part.getHeader(headerName));
//        }
//
//        String saveName = System.currentTimeMillis() + "." + fileName;
//
//        logger.info("save the file with new name : " + saveName);
//
//        // 因在注解中指定了路径，这里可以指定要写入的文件名
//        // 在未执行write方法之前，将会在注解指定location路径下生成一临时文件
//        part.write(saveName);
//    }
//
//}