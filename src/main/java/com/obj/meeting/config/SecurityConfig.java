package com.obj.meeting.config;

import com.obj.meeting.filter.JwtAuthFilter;
import com.obj.meeting.filter.LoginAuthFilter;
import com.obj.meeting.oauth2.MeetingOAuth2SuccessHandler;
import com.obj.meeting.service.MeetingOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAuth jwtAuth;
    private final MeetingOAuth2UserService meetingOAuth2UserService;
    private final MeetingOAuth2SuccessHandler meetingOAuth2SuccessHandler;


    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtAuth jwtAuth
            ,MeetingOAuth2UserService meetingOAuth2UserService
            , MeetingOAuth2SuccessHandler meetingOAuth2SuccessHandler
    ) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtAuth = jwtAuth;
        this.meetingOAuth2UserService = meetingOAuth2UserService;
        this.meetingOAuth2SuccessHandler = meetingOAuth2SuccessHandler;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return (request,response,authentication)->{
            String username = authentication.getName();
            String role = authentication.getAuthorities().iterator().next().getAuthority();

            // JWT 생성
            String token = jwtAuth.generateToken(username, role);
            response.addHeader("Authorization", "Bearer " + token);


        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 인가처리
        http.authorizeHttpRequests(auth->auth
                .requestMatchers("/getUserList").hasRole("ADMIN")
                .requestMatchers("/getGroupList","/saveGroup").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll()
        );


        http.csrf(auth->auth.disable());


        http.formLogin(form->form
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
//                .defaultSuccessUrl("/menu")
                .permitAll());

        http.oauth2Login(oauth2->oauth2
                .loginPage("/login")
                .userInfoEndpoint(config->config.userService(meetingOAuth2UserService))
                .successHandler(meetingOAuth2SuccessHandler)

        );


        http.headers(headers->headers
                .frameOptions(frame->frame
                        .disable()));



        http.logout(logout->logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index.html")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
        );



        http.exceptionHandling(exception->exception.accessDeniedPage("/forbidden"));
        http.addFilterBefore(new JwtAuthFilter(jwtAuth),UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(new JwtAuthFilter(jwtAuth),LoginAuthFilter.class);
//        http.addFilterAt(new LoginAuthFilter(authenticationManager(authenticationConfiguration), jwtAuth)
//                , UsernamePasswordAuthenticationFilter.class);

        // 세션 유지 X
        http.sessionManagement(session ->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
