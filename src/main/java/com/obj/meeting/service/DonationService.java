package com.obj.meeting.service;


import com.obj.meeting.entity.DonationEntity;
import com.obj.meeting.entity.GroupEntity;
import com.obj.meeting.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    // 기부 생성
    public String save(DonationEntity donation) {
        donationRepository.save(donation);
        return "success";
    }
    //기부 리스트
    public List<DonationEntity> getDonationList() {
        return donationRepository.findAll();
    }

//기부 상세정보
    // 1. 기부 참여
    public DonationEntity getDonation(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("기부를 찾을 수 없습니다."));
    }


}
