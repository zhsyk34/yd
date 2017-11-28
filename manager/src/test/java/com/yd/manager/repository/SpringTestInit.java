package com.yd.manager.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yd.manager.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class SpringTestInit {
    @PersistenceContext
    protected EntityManager manager;

    @Autowired
    protected ManagerRepository managerRepository;
    @Autowired
    protected MerchandiseRepository merchandiseRepository;
    @Autowired
    protected OrdersRepository ordersRepository;
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ObjectMapper mapper;
}
