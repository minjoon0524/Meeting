package com.obj.meeting.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="donation")
@Data
public class DonationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기부id
    private String name; //기부명
    private String creator; // 기부 개설자 id
    private String description; //기부설명
    private double donationAmount; //기부금액
    private double totalAmount;
    private Date dueDate; // 기부 마감일자
    private int donorCount; // 기부자 수

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "donation_user"
            ,joinColumns = @JoinColumn(name = "id") //내쪽
            ,inverseJoinColumns = @JoinColumn(name = "username") //상대편
    )
    private List<UserEntity> participants=new ArrayList<>();
}
