//package com.yd.apk.ref;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
//public class DownloadFileServlet2 extends HttpServlet {
//
//    protected void doGet(HttpServletRequest request,
//                         HttpServletResponse response) throws ServletException, IOException {
//        // reads input file from an absolute path
//        String filePath = "E:/Test/Download/MYPIC.JPG";
//        File downloadFile = new File(filePath);
//        FileInputStream inStream = new FileInputStream(downloadFile);
//
//        // if you want to use a relative path to context root:
//        String relativePath = getServletContext().getRealPath("");
//        System.out.println("relativePath = " + relativePath);
//
//        // obtains ServletContext
//        ServletContext context = getServletContext();
//
//        // gets MIME type of the file
//        String mimeType = context.getMimeType(filePath);
//        if (mimeType == null) {
//            // set to binary type if MIME mapping not found
//            mimeType = "application/octet-stream";
//        }
//        System.out.println("MIME type: " + mimeType);
//
//        // modifies response
//        response.setContentType(mimeType);
//        response.setContentLength((int) downloadFile.length());
//
//        // forces download
//        String headerKey = "Content-Disposition";
//        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
//        response.setHeader(headerKey, headerValue);
//
//        // obtains response's output stream
//        OutputStream outStream = response.getOutputStream();
//
//        byte[] buffer = new byte[4096];
//        int bytesRead = -1;
//
//        while ((bytesRead = inStream.read(buffer)) != -1) {
//            outStream.write(buffer, 0, bytesRead);
//        }
//
//        inStream.close();
//        outStream.close();
//    }
//}