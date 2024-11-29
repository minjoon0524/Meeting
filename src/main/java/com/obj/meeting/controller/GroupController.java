package com.obj.meeting.controller;

import com.obj.meeting.dto.Group;
import com.obj.meeting.dto.RequestGroup;
import com.obj.meeting.entity.GroupEntity;
import com.obj.meeting.entity.UserEntity;
import com.obj.meeting.service.GroupService;
import com.obj.meeting.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;
    @GetMapping("/get/{groupId}")
    public GroupEntity getGroup(@PathVariable Long groupId) {
        return groupService.getGroup(groupId);
    }

    @GetMapping("/list")
    public List<GroupEntity> getGroupList() {
        return groupService.getGroupList();
    }


    private Path location;

    @PostConstruct
    public void init(){
    this.location= Paths.get("C:\\upload");
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveGroup(RequestGroup group,@RequestParam("groupImage") MultipartFile groupImage) throws IOException {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupName(group.getGroupName());
        groupEntity.setGroupDescription(group.getGroupDescription());
        groupEntity.setGroupManagerId(group.getGroupManagerId());
        groupEntity.setMeetingDate(group.getMeetingDate());
        groupEntity.setMeetingAddress(group.getMeetingAddress());

        String fileName= StringUtils.cleanPath(groupImage.getOriginalFilename());
        Path filePath=location.resolve(fileName).normalize();

        groupImage.transferTo(filePath);
        groupEntity.setGroupImage("/"+fileName);
        groupService.save(groupEntity);
        return ResponseEntity.ok("모임 등록이 완료되었습니다.");

    }

    @PostMapping("/update")
    public void modify(
            GroupEntity group) {
        groupService.updateEntity(group);
    }

    @PostMapping("/delete/{groupId}")
    public void delete(@PathVariable Long groupId) {
        groupService.deleteEntity(groupId);
    }



    @Transactional
    @PostMapping("/join/{groupId}")
    public ResponseEntity<?> join(@PathVariable(name = "groupId") Long groupId, @RequestParam String username) {
        GroupEntity group = groupService.getGroup(groupId);

        UserEntity user = userService.getUser(username);

        if(group.getParticipants()==null){
            group.setParticipants(new ArrayList<>());
        }

        if(group.getParticipants().contains(user)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 참여하였습니다.");
        }else {
            group.getParticipants().add(user);
        }
        groupService.save(group);
        return ResponseEntity.ok("참여가 완료되었습니다.");

    }

    //
    @GetMapping("/get/{groupId}/participant")
    public ResponseEntity<List<UserEntity>> getGroupParticipant(@PathVariable(name = "groupId") Long groupId) {
        GroupEntity group = groupService.getGroup(groupId);

        List<UserEntity> participants=group.getParticipants();
        return ResponseEntity.ok(participants);
    }
}

