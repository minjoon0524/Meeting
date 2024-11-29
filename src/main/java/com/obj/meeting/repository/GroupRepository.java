package com.obj.meeting.repository;

import com.obj.meeting.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {

    Optional<GroupEntity> findById(Long id);

    GroupEntity findByGroupName(String groupName);




}
