package com.cat.zsy;

import com.cat.zsy.servlet.UserServlet;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class Entry extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add(UserServlet.class);
        return set;
    }
}
