package com.cat.zsy.servlet;

import com.cat.zsy.domain.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UserServlet {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> list() {
        List<User> list = new ArrayList<>();
        list.add(User.builder().id(100L).name("csl").build());
        list.add(User.builder().id(212L).name("zsy").build());
        return list;
    }

    @GET
    @Path("/test")
    public String test() {
        return "Hello World.";
    }
}
