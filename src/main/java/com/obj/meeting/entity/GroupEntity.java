package com.obj.meeting.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groupdetails")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(unique = false, nullable = true)
    private String groupName;

    @Column(unique = false, nullable = true)
    private String groupManagerId;

    @Column(unique = false, nullable = true)
    private String groupDescription;

    @Column(unique = false, nullable = true)
    private String GroupImage;

    @Column(unique = false, nullable = true)
    private Date meetingDate;

    @Column(unique = false, nullable = true)
    private String meetingAddress;

    @Column(unique = false, nullable = true)
    private int participantCount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_user"
            ,joinColumns = @JoinColumn(name = "groupId") //내쪽
            ,inverseJoinColumns = @JoinColumn(name = "username") //상대편
    )
    private List<UserEntity> participants=new ArrayList<>();

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupManagerId() {
        return groupManagerId;
    }

    public void setGroupManagerId(String groupManagerId) {
        this.groupManagerId = groupManagerId;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupImage() {
        return GroupImage;
    }

    public void setGroupImage(String groupImage) {
        GroupImage = groupImage;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public List<UserEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserEntity> participants) {
        this.participants = participants;
    }
}
