package com.example.jwt.filter;

import javax.servlet.*;
import java.io.IOException;

public class Myfilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("필터2");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
