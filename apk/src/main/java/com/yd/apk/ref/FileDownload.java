//package com.yd.apk.ref;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
//@WebServlet("/filedownload")
//public class FileDownload extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public FileDownload() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//    /**
//     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String dataDirectory = request.
//                getServletContext().getRealPath("/WEB-INF/");
//        String filename = "Java Persistence with MyBatis 3.pdf";
//        File file = new File(dataDirectory, filename);
//        if (file.exists()) {
//            response.setContentType("application/pdf");
//            response.addHeader("Content-Disposition",
//                    "attachment; filename=\"" + filename + "\"");
//            byte[] buffer = new byte[1024];
//            FileInputStream fis = null;
//            BufferedInputStream bis = null;
//
//            try {
//                fis = new FileInputStream(file);
//                bis = new BufferedInputStream(fis);
//                OutputStream os = response.getOutputStream();
//                int i = bis.read(buffer);
//                while (i != -1) {
//                    os.write(buffer, 0, i);
//                    i = bis.read(buffer);
//                }
//            } catch (IOException ex) {
//                System.out.println(ex.toString());
//            } finally {
//                if (bis != null) {
//                    bis.close();
//                }
//                if (fis != null) {
//                    fis.close();
//                }
//            }
//        }
//    }
//
//    /**
//     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//
//}