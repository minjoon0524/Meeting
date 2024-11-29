package com.obj.meeting.oauth2;

import com.obj.meeting.config.JwtAuth;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MeetingOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private JwtAuth jwtAuth;

    public MeetingOAuth2SuccessHandler(JwtAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        String role=authentication.getAuthorities().iterator().next().getAuthority();

        String token=jwtAuth.generateToken(username,role);

        Cookie cookie=new Cookie("Authorization",token);
        cookie.setHttpOnly(false); //자바스크립트 쿠키 접근
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect("/menu");
    }
}
