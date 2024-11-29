package com.obj.meeting.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class RequestGroup {
    private Long groupId;
    private String groupName;
    private String groupManagerId;
    private String groupDescription;

    private Date meetingDate;
    private String meetingAddress;
    private int participantCount;
}
