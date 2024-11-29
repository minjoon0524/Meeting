package com.obj.meeting.service;

import com.obj.meeting.dto.MeetingOAuth2User;
import com.obj.meeting.entity.UserEntity;
import com.obj.meeting.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MeetingOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public MeetingOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String username = null;
        String email = null;
        String name = null;

        // 사용자 정보 처리
        Map<String, Object> attributes = oAuth2User.getAttributes();
        switch (registrationId) {
            case "naver":
                Map<String, Object> naverResponse = (Map<String, Object>) attributes.get("response");
                if (naverResponse != null) {
                    username = naverResponse.get("id").toString();
                    email = (String) naverResponse.get("email");
                    name = (String) naverResponse.get("name");
                }
                break;

            case "kakao":

                username = attributes.get("id").toString();
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");


                if (kakaoProfile != null) {
                    name = (String) kakaoProfile.get("nickname");
                } else {

                    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
                    if (properties != null) {
                        name = (String) properties.get("nickname");
                    }
                }
                break;

            case "google":
                return null;

            default:
                return null;
        }

        System.out.println("=== Username === " + username);
        System.out.println("=== Email === " + email);
        System.out.println("=== Name === " + name);


        String userKey = registrationId + "_" + username;
        UserEntity existingUser = userRepository.findByUsername(userKey);

        if (existingUser == null) {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(userKey);
            newUser.setEmail(email);
            newUser.setRole("ROLE_ADMIN");
            userRepository.save(newUser);
            return new MeetingOAuth2User(newUser);
        } else {
            existingUser.setEmail(email);
            userRepository.save(existingUser);
            return new MeetingOAuth2User(existingUser);
        }
    }
}
