package com.example.jwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Myfilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // 토큰 : 코스 (한글 안됨) 이걸 만들어줘야 함, id/pw 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어주고 응답.
        // 요청할 때마다 header에 Authoriztion에 value 값으로 토큰을 가지고옴
        // 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이랑 맞는지만 검증 (RSA, HS256)
        // private 키로 토큰을 보내고 클라이언트에서 private key로 보낼 시 내가 public key로 열었을때 검증이 되면 reponse가 되도록 설계
        /*
        if (req.getMethod().equals("POST")){
            System.out.println("post 시에 작동됨");
            String headerAuth = req.getHeader("Authorizetion");
            System.out.println("header : " + headerAuth);

            if (headerAuth.equals("cos")){
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                PrintWriter out = res.getWriter();
                out.println("인증 안됨");
            }
        }
         */
    }
}
