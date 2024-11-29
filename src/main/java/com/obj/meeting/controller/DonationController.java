package com.obj.meeting.controller;

import com.obj.meeting.dto.RequestDonation;
import com.obj.meeting.entity.DonationEntity;
import com.obj.meeting.entity.UserEntity;
import com.obj.meeting.service.DonationService;
import com.obj.meeting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donation")
public class DonationController {
    private final DonationService donationService;
    private final UserService userService;

    //기부생성
    @PostMapping("/save")
    public ResponseEntity<?> saveDonation(RequestDonation donation) {
        DonationEntity donationEntity = new DonationEntity();

        donationEntity.setName(donation.getName());
        donationEntity.setCreator(donation.getCreator());
        donationEntity.setDescription(donation.getDescription());
        donationEntity.setDonationAmount(donation.getDonationAmount());
        donationEntity.setDueDate(donation.getDueDate());

        donationService.save(donationEntity);
        return ResponseEntity.ok("기부 등록이 완료되었습니다.");

    }

    // 기부 리스트 
    @GetMapping("/list")
    public List<DonationEntity> getdonationList() {
        return donationService.getDonationList();
    }

    @GetMapping("/get/{id}")
    public DonationEntity getDonation(@PathVariable(name = "id") Long id) {
        return donationService.getDonation(id);
    }

    // 기부 참여
    @Transactional
    @PostMapping("/join/{id}")
    public ResponseEntity<?> join(@PathVariable(name = "id") Long donationId, @RequestParam String username) {
        DonationEntity donation = donationService.getDonation(donationId);

        UserEntity user = userService.getUser(username);

        if(donation.getParticipants()==null){
            donation.setParticipants(new ArrayList<>());
        }

        if(donation.getParticipants().contains(user)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 참여하였습니다.");

        }else {
            donation.getParticipants().add(user);

            donation.setDonorCount(donation.getDonorCount()+1);
            donation.setTotalAmount(donation.getDonationAmount()*donation.getDonorCount());
        }
        donationService.save(donation);
        return ResponseEntity.ok("참여가 완료되었습니다.");

    }


    @GetMapping("/get/{id}/donors")
    public ResponseEntity<List<UserEntity>> getDonationParticipant(@PathVariable(name = "id") Long donationId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();


        DonationEntity donation = donationService.getDonation(donationId);


        if (!donation.getCreator().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);  // 기부 개설자가 아니면 접근 거부
        }


        List<UserEntity> participants = donation.getParticipants();
        return ResponseEntity.ok(participants);
    }


}
