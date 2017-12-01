package com.yd.manager.controller;

import com.yd.manager.repository.*;
import com.yd.manager.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

abstract class CommonController {
    @Setter(onMethod = @__({@Autowired}))
    protected MerchandiseRepository merchandiseRepository;
    @Setter(onMethod = @__({@Autowired}))
    protected UserRepository userRepository;
    @Setter(onMethod = @__({@Autowired}))
    protected ManagerRepository managerRepository;
    @Setter(onMethod = @__({@Autowired}))
    protected OrdersRepository ordersRepository;

    @Setter(onMethod = @__({@Autowired}))
    protected UserService userService;

//    @PostMapping
//    protected abstract Result<Boolean> create(T t);
//
//    @GetMapping
//    protected abstract Result<Page<T>> retrieve(T t, Pageable pageable);
//
//    @PutMapping
//    protected abstract Result<Boolean> update(T t);
//
//    @DeleteMapping
//    protected abstract Result<Boolean> delete(K k);
}
