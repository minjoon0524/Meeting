package com.obj.meeting.dto;


import lombok.Data;

@Data
public class User {

    private String username; //id역할
    private String password;
    private String email;
    private String role; // 권한체크
}
