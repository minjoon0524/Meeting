package com.obj.meeting.repository;

import com.obj.meeting.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
//    @Override
//    Optional<UserEntity> findById(Long id);

    UserEntity findByUsername(String username);
}
