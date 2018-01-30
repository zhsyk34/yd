//package com.yd.ecabinet.server.rfid;
//
//public class TagTest {
//
//    public static void main(String[] args) {
//        TidRepositoryImpl service = new TidRepositoryImpl();
//        for (int i = 1; i < 5; i++) {
//            service.add("" + i);
//        }
//
//        service.init();
//
//        service.add("1");
//        service.add("2");
//
//        System.out.println(service);
//        System.out.println(service.getDelta());
//
//        service.add("1");
//
//        System.out.println(service);
//        System.out.println(service.getDelta());
//    }
//}