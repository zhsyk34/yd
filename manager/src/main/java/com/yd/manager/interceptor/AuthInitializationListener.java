package com.yd.manager.interceptor;

import com.yd.manager.entity.Manager;
import com.yd.manager.repository.ManagerRepository;
import com.yd.manager.util.PhpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限初始化
 */
//@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Deprecated
public class AuthInitializationListener implements ServletContextListener {

    private static final Map<Manager, List<Long>> map = new HashMap<>(1 << 8);

    private final ManagerRepository managerRepository;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        this.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private void init() {
        logger.info("----------加载admin信息----------");
        List<Manager> managers = managerRepository.findAll();

        managers.forEach(manager -> {
            if (manager.getType() > 1) {
                List<Long> stores = PhpUtils.parse(manager.getStores());
                map.put(manager, stores);
            } else {
                map.put(manager, null);
            }
        });

        logger.info("----------加载admin完毕----------");
        map.forEach((m, s) -> logger.info("{} 拥有权限 {}", m.getName(), m.getType() > 1 ? StringUtils.collectionToDelimitedString(s, ",") : "管理员"));
    }
}
