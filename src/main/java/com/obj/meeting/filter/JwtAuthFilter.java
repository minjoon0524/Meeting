package com.obj.meeting.filter;

import com.obj.meeting.config.JwtAuth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtAuth jwtAuth;

    public JwtAuthFilter(JwtAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token=null;

        if(authorization!=null && authorization.startsWith("Bearer ")) {
            token=authorization.substring(7);
        }else {
            filterChain.doFilter(request, response);
            return;
        }


        String username=jwtAuth.getUserName(token);
        String role=jwtAuth.getRole(token);

        UserDetails userDetails=new User(username,"", Collections.singletonList(new SimpleGrantedAuthority(role)));
        Authentication authentication =new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
