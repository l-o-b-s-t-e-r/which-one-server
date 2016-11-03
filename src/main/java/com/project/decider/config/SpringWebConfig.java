package com.project.decider.config;

import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lobster on 30.04.16.
 */

@EnableWebMvc
@Configuration
@ComponentScan({"com.project.decider.controller"})
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public VelocityConfigurer velocityConfig() {
        Map<String, Object> velocityProperties = new HashMap<>();
        velocityProperties.put("eventhandler.include.class", IncludeRelativePath.class.getName());
        velocityProperties.put("input.encoding", "utf-8");
        velocityProperties.put("output.encoding", "utf-8");
        VelocityConfigurer cfg = new VelocityConfigurer();
        cfg.setResourceLoaderPath("/WEB-INF/views/");
        cfg.setVelocityPropertiesMap(velocityProperties);
        return cfg;
    }

    @Bean
    public VelocityViewResolver viewResolver() {
        VelocityViewResolver viewResolver = new VelocityViewResolver();
        viewResolver.setViewClass(VelocityToolboxView.class);
        viewResolver.setSuffix(".vm");
        viewResolver.setContentType("text/html; charset=utf-8");
        return viewResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(5242880);
        return resolver;
    }
}
