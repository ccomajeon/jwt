package com.example.jwt.config;

import com.example.jwt.filter.Myfilter1;
import com.example.jwt.filter.Myfilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 필터를 직접 만든다.
// Request 가 오면 동작
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Myfilter1> filter1() {
        FilterRegistrationBean<Myfilter1> bean = new FilterRegistrationBean<>(new Myfilter1());
        bean.addUrlPatterns("/*");
        bean.setOrder(0);   // 낮은 번호가 필터중에서 가장 먼저 실행됨;

        return bean;
    }

    @Bean
    public FilterRegistrationBean<Myfilter2> filter2() {
        FilterRegistrationBean<Myfilter2> bean = new FilterRegistrationBean<>(new Myfilter2());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);   // 낮은 번호가 필터중에서 가장 먼저 실행됨;

        return bean;
    }
}
