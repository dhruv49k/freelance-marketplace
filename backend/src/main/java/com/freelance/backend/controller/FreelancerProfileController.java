package com.freelance.backend.controller;

import com.freelance.backend.dto.FreelancerProfileRequest;
import com.freelance.backend.dto.FreelancerProfileResponse;
import com.freelance.backend.service.FreelancerProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/freelancers")
public class FreelancerProfileController {
    private final FreelancerProfileService freelancerProfileService;
    public FreelancerProfileController(
            FreelancerProfileService freelancerProfileService) {

        this.freelancerProfileService = freelancerProfileService;
    }
    @PostMapping("/profile")
    public FreelancerProfileResponse createProfile(
            @RequestBody FreelancerProfileRequest request) {

        return freelancerProfileService.createProfile(request);
    }
    @GetMapping("/profile")
    public FreelancerProfileResponse getMyProfile() {

        return freelancerProfileService.getMyProfile();
    }
    @PutMapping("/profile")
    public FreelancerProfileResponse updateProfile(
            @RequestBody FreelancerProfileRequest request) {

        return freelancerProfileService.updateProfile(request);
    }
}
