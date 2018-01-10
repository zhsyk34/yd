package com.yd.ecabinet.service;

import com.yd.ecabinet.config.SpringConfig;
import com.yd.ecabinet.domain.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class ServiceTest {

    @Autowired
    private OpenSignalListener openSignalListener;
    @Autowired
    private PhpService phpService;
    @Autowired
    private TagService tagService;

    @Test
    public void script() {
        System.out.println(tagService.read());
    }

    @Test
    public void listen() throws InterruptedException {
        openSignalListener.listen();
        new CountDownLatch(1).await();
    }

    @Test
    public void phpTest() {
        List<Stock> stocks = Arrays.asList(
                Stock.of("6917878027333", 3),
                Stock.of("6928804014649", 2)
        );
        System.out.println(stocks);
        phpService.submitOrder(stocks);
    }

    @Test
    public void phpTest2() {
        System.out.println(phpService.querySignal());
    }
}