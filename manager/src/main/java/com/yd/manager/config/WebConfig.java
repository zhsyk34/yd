package com.yd.manager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WebConfig extends WebMvcConfigurerAdapter {

    private final PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(pageableArgumentResolver);
        pageableArgumentResolver.setFallbackPageable(new PageRequest(0, 10));
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jackson2HttpMessageConverter);
        super.configureMessageConverters(converters);
    }

}