package com.example.jwt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.auth.PrincipalDetails;
import com.example.jwt.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 제공
// /login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println(" 로그인 시도중");

        // 1. memeberName, password 받아서
        try {
//            BufferedReader dr = request.getReader();
//            String input = null;
//
//            while ((input = dr.readLine()) != null){
//                System.out.println(input);
//            }
//            System.out.println(request.getInputStream().toString());
            ObjectMapper om = new ObjectMapper();
            Member member = om.readValue(request.getInputStream(), Member.class);
            System.out.println(member);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getMemberName(), member.getPassword());

            // PrincipalDetailsService 가 실행된다. 정상이면 authentication이 리턴됨 강제x
            // DB에 있는 memberName과 password가 일치
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("이 아이디로 로그인 성공 : "+principalDetails.getMember().getMemberName());   // 로그인이 정상적으로 되었다.

            // 세션에 저장이됨. 그 방법은 return
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 하는것
            // jwt 토큰을 사용하면 세션을 만들 이유가 없음. 근데 단지 권한 처리때문에 session에 넣어둠

            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 2. 정상인지 로그인 시도를 해봄. 받아온 authenticationManager으로 로그인 시도를 하면
        // PrincipalDetailsService 가 실행된다.

        // 3. PrincipalDetails를 session에 담음 (권한 관리를 위해 담음)
        // 4. JWT토큰을 만들어서 응답해줌.
    }

    // attemptAuthentication실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨.
    // JWT 토큰을 만들어서 request요청한 사용자에게 response를 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행 -> 인증성공");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // RSA방식이 아닌 Hash 암호 방식
        String jwtToken = JWT.create()
                .withSubject("cos 토큰")                                          // 토큰명
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))  // 토큰 만료 시간
                .withClaim("id", principalDetails.getMember().getId())           // 토큰 id
                .withClaim("userName", principalDetails.getMember().getMemberName())     // 토큰 name
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));                           // 토큰 sign

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX);

        /*
            기존 방식

            1. 로그인 정상
            2. 서버쪽에서 sessionId 생성
            3. 클라이언트 cookie sessionId 응답
            4. 요청 할때마다 sessionId를 들고 서버에 요청 ex) session.getAttribute("세션값")
            5. 유효한지 확인 후 접근

            JWT 방식
            1. 로그인 정상
            2. JWT토큰 생성
            3. 클라이언트 쪽 -> JWT토큰 응답
            4. 요청할 때마다 JWT토큰을 가지고 요청
            5. 서버는 JWT토큰이 유효한지 판단 (Filter)
         */
    }
}
