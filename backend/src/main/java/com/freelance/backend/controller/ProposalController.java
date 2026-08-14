package com.freelance.backend.controller;

import com.freelance.backend.service.ProposalService;
import org.springframework.web.bind.annotation.*;
import com.freelance.backend.dto.ProposalRequest;
import com.freelance.backend.dto.ProposalResponse;
import com.freelance.backend.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @PostMapping("/{projectId}/proposals")
    public ResponseEntity<ProposalResponse> createProposal(
            @PathVariable Long projectId,
            @Valid @RequestBody ProposalRequest request
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User freelancer = (User) authentication.getPrincipal();

        ProposalResponse response =
                proposalService.createProposal(
                        projectId,
                        request,
                        freelancer
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{projectId}/proposals")
    public ResponseEntity<List<ProposalResponse>> getProposalsForProject(
            @PathVariable Long projectId
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User client = (User) authentication.getPrincipal();

        List<ProposalResponse> proposals =
                proposalService.getProposalsForProject(
                        projectId,
                        client
                );

        return ResponseEntity.ok(proposals);
    }
}