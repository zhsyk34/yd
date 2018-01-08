package com.yd.manager.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ManagerRepositoryTest extends SpringTestInit {

    @Test
    public void findAll() throws Exception {
        managerRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void findByPhone() {
        managerRepository.findByPhone("132");
    }
}