package com.mkanchwala.s3;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.mkanchwala.s3" })
public class WebConfig extends WebMvcConfigurerAdapter {
 
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	converters.add(new BufferedImageHttpMessageConverter());
    	converters.add(new MappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }
}