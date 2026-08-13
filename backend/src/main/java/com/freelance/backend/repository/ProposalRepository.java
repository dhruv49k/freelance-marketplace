package com.freelance.backend.repository;

import com.freelance.backend.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    boolean existsByProjectIdAndFreelancerId(
            Long projectId,
            Long freelancerId
    );

    List<Proposal> findByProjectId(Long projectId);

    List<Proposal> findByFreelancerId(Long freelancerId);
}