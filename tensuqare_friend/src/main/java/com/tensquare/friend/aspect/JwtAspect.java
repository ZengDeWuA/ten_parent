package com.tensquare.friend.aspect;

import config.JwtUtil;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class JwtAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Pointcut(value = "execution(public * com.tensquare.friend.controller.*.*(..))")
    public void cut(){}

    @Before("cut()")
    public void before(){
        String header = request.getHeader("Authorization");
        if (header!=null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if (claims!=null && "admin".equals(claims.get("roles"))){
                request.setAttribute("admin_claims", claims);
            }
            if (claims!=null && "user".equals(claims.get("roles"))){
                request.setAttribute("user_claims", claims);
            }

        }
    }
}
