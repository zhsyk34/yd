package com.yd.manager.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yd.manager.listener.AuthInitializationListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.servlet.ServletContextListener;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Configuration
public class BeanConfig {
    @Bean
    @Primary
    public SimpleModule simpleModule(List<JsonSerializer<?>> serializers) {
        SimpleModule module = new SimpleModule();
        serializers.forEach(module::addSerializer);
        return module;
    }

    @Bean
    public PageableArgumentResolver pageableArgumentResolver() {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(new PageRequest(0, 10, DESC, "id"));
        return resolver;
    }

    @Bean
    @Primary
    public MappingJackson2HttpMessageConverter objectMapper(Jackson2ObjectMapperBuilder builder, SimpleModule module) {
        ObjectMapper mapper = builder.build();

        mapper.registerModule(module);
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return new MappingJackson2HttpMessageConverter(mapper);
    }

    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> authListener(AuthInitializationListener authInitializationListener) {
        return new ServletListenerRegistrationBean<>(authInitializationListener);
    }

}
