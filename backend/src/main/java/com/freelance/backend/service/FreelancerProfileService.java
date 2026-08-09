package com.freelance.backend.service;

import com.freelance.backend.dto.FreelancerProfileRequest;
import com.freelance.backend.dto.FreelancerProfileResponse;
import com.freelance.backend.entity.FreelancerProfile;
import com.freelance.backend.entity.User;
import com.freelance.backend.repository.FreelancerProfileRepository;
import com.freelance.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.freelance.backend.entity.Role;

@Service
public class FreelancerProfileService {
    private final UserRepository userRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;

    public FreelancerProfileService(
            UserRepository userRepository,
            FreelancerProfileRepository freelancerProfileRepository) {

        this.userRepository = userRepository;
        this.freelancerProfileRepository = freelancerProfileRepository;
    }
    public FreelancerProfileResponse createProfile(
            FreelancerProfileRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        if (user.getRole() != Role.FREELANCER) {
            throw new RuntimeException(
                    "Only freelancers can create a freelancer profile");
        }
        if (freelancerProfileRepository
                .findByUserId(user.getId())
                .isPresent()) {

            throw new RuntimeException(
                    "Freelancer profile already exists");
        }
        FreelancerProfile profile =
                new FreelancerProfile();
        profile.setUser(user);
        profile.setTitle(request.getTitle());
        profile.setBio(request.getBio());
        profile.setSkills(request.getSkills());
        profile.setExperience(request.getExperience());
        profile.setHourlyRate(request.getHourlyRate());

        FreelancerProfile savedProfile =
                freelancerProfileRepository.save(profile);
        FreelancerProfileResponse response =
                new FreelancerProfileResponse();
        response.setId(savedProfile.getId());
        response.setTitle(savedProfile.getTitle());
        response.setBio(savedProfile.getBio());
        response.setSkills(savedProfile.getSkills());
        response.setExperience(savedProfile.getExperience());
        response.setHourlyRate(savedProfile.getHourlyRate());
        response.setCreatedAt(savedProfile.getCreatedAt());

        return response;
    }

    public FreelancerProfileResponse getMyProfile() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        FreelancerProfile profile =
                freelancerProfileRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Freelancer profile not found"));

        FreelancerProfileResponse response =
                new FreelancerProfileResponse();

        response.setId(profile.getId());
        response.setTitle(profile.getTitle());
        response.setBio(profile.getBio());
        response.setSkills(profile.getSkills());
        response.setExperience(profile.getExperience());
        response.setHourlyRate(profile.getHourlyRate());
        response.setCreatedAt(profile.getCreatedAt());

        return response;
    }

    public FreelancerProfileResponse updateProfile(
            FreelancerProfileRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        FreelancerProfile profile =
                freelancerProfileRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Freelancer profile not found"));

        profile.setTitle(request.getTitle());
        profile.setBio(request.getBio());
        profile.setSkills(request.getSkills());
        profile.setExperience(request.getExperience());
        profile.setHourlyRate(request.getHourlyRate());

        FreelancerProfile updatedProfile =
                freelancerProfileRepository.save(profile);

        FreelancerProfileResponse response =
                new FreelancerProfileResponse();

        response.setId(updatedProfile.getId());
        response.setTitle(updatedProfile.getTitle());
        response.setBio(updatedProfile.getBio());
        response.setSkills(updatedProfile.getSkills());
        response.setExperience(updatedProfile.getExperience());
        response.setHourlyRate(updatedProfile.getHourlyRate());
        response.setCreatedAt(updatedProfile.getCreatedAt());

        return response;
    }
}