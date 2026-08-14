package com.freelance.backend.service;

import com.freelance.backend.entity.*;
import com.freelance.backend.exception.DuplicateProposalException;
import com.freelance.backend.exception.ProjectNotFoundException;
import com.freelance.backend.dto.ProposalRequest;
import com.freelance.backend.dto.ProposalResponse;
import com.freelance.backend.repository.ProposalRepository;
import com.freelance.backend.repository.ProjectRepository;
import com.freelance.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.freelance.backend.exception.UnauthorizedProjectAccessException;
import java.util.List;

@Service
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProposalService(
            ProposalRepository proposalRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.proposalRepository = proposalRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProposalResponse createProposal(
            Long projectId,
            ProposalRequest request,
            User freelancer
    ) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ProjectNotFoundException("Project not found"));

        if (project.getStatus() != ProjectStatus.OPEN) {
            throw new IllegalStateException(
                    "Cannot submit proposal for a closed project"
            );
        }

        if (proposalRepository.existsByProjectIdAndFreelancerId(
                projectId,
                freelancer.getId()
        )) {
            throw new DuplicateProposalException(
                    "You have already submitted a proposal for this project"
            );
        }

        Proposal proposal = new Proposal();

        proposal.setProject(project);
        proposal.setFreelancer(freelancer);
        proposal.setBidAmount(request.getBidAmount());
        proposal.setCoverLetter(request.getCoverLetter());
        proposal.setStatus(ProposalStatus.PENDING);

        Proposal savedProposal = proposalRepository.save(proposal);

        ProposalResponse response = new ProposalResponse();

        response.setId(savedProposal.getId());
        response.setProjectId(savedProposal.getProject().getId());
        response.setFreelancerId(savedProposal.getFreelancer().getId());
        response.setBidAmount(savedProposal.getBidAmount());
        response.setCoverLetter(savedProposal.getCoverLetter());
        response.setStatus(savedProposal.getStatus());
        response.setCreatedAt(savedProposal.getCreatedAt());

        return response;
    }

    public List<ProposalResponse> getProposalsForProject(
            Long projectId,
            User client
    ) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ProjectNotFoundException("Project not found"));

        if (!project.getClient().getId().equals(client.getId())) {
            throw new UnauthorizedProjectAccessException(
                    "You are not authorized to view proposals for this project"
            );
        }

        List<Proposal> proposals =
                proposalRepository.findByProjectId(projectId);

        return proposals.stream()
                .map(proposal -> {
                    ProposalResponse response = new ProposalResponse();

                    response.setId(proposal.getId());
                    response.setProjectId(proposal.getProject().getId());
                    response.setFreelancerId(proposal.getFreelancer().getId());
                    response.setBidAmount(proposal.getBidAmount());
                    response.setCoverLetter(proposal.getCoverLetter());
                    response.setStatus(proposal.getStatus());
                    response.setCreatedAt(proposal.getCreatedAt());

                    return response;
                })
                .toList();
    }
}