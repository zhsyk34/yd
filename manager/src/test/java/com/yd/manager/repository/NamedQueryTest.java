package com.yd.manager.repository;

import org.hibernate.Session;
import org.junit.Test;

import javax.transaction.Transactional;

@Transactional
public class NamedQueryTest extends SpringTestInit {

    @Test
    public void name() throws Exception {
        Session session = manager.unwrap(Session.class);
        System.out.println(session);

        session
                .getNamedQuery("test")
                .list();
    }
}
