package com.obj.meeting.service;

import com.obj.meeting.dto.User;
import com.obj.meeting.entity.UserEntity;
import com.obj.meeting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public String save(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "success";
    }


    public UserEntity getUser(String username) {

        return userRepository.findByUsername(username);
    }

    public List<UserEntity> getUserList() {
        return userRepository.findAll();
    }


}
