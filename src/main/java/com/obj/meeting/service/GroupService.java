package com.obj.meeting.service;

import com.obj.meeting.entity.GroupEntity;
import com.obj.meeting.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public String save(GroupEntity group) {
        groupRepository.save(group);
        return "success";
    }


    public GroupEntity getGroup(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("그룹을 찾을 수 없습니다."));
    }

    public List<GroupEntity> getGroupList() {
        return groupRepository.findAll();
    }

    public  void updateEntity(GroupEntity group) {
        groupRepository.save(group);
    }

    public void deleteEntity(Long groupId) {
        groupRepository.deleteById(groupId);
    }


//    public void deleteGroup(Long groupId) {
//        groupRepository.deleteById(groupId);
//    }
}
