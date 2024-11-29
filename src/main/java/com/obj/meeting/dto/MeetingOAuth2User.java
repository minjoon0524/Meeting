package com.obj.meeting.dto;

import com.obj.meeting.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MeetingOAuth2User implements OAuth2User {
    private UserEntity userEntity;

    public MeetingOAuth2User(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole();
            }
        });
        return authorities;
    }

    @Override
    public String getName() {
        return userEntity.getUsername();
    }

    public String getUserName() {
        return userEntity.getUsername();
    }
}
