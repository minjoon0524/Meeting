package com.obj.meeting.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class RequestDonation {
    private Long id; // 기부id
    private String name; //기부명
    private String creator; // 기부 개설자 id
    private String description; //기부설명
    private double donationAmount; //기부금액
    private double totalAmount;
    private Date dueDate; // 기부 마감일자
    private int donorCount; // 기부자 수
}
