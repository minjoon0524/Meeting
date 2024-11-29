package com.obj.meeting.repository;

import com.obj.meeting.entity.DonationEntity;
import com.obj.meeting.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity,Long>  {
}
