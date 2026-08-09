package com.freelance.backend.repository;

import com.freelance.backend.entity.FreelancerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FreelancerProfileRepository
        extends JpaRepository<FreelancerProfile, Long> {
    Optional<FreelancerProfile> findByUserId(Long userId);
}