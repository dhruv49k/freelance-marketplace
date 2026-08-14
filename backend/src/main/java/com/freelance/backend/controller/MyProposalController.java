package com.freelance.backend.controller;

import com.freelance.backend.dto.ProposalResponse;
import com.freelance.backend.entity.User;
import com.freelance.backend.service.ProposalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/proposals")
public class MyProposalController {

    private final ProposalService proposalService;

    public MyProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProposalResponse>> getMyProposals() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User freelancer = (User) authentication.getPrincipal();

        List<ProposalResponse> proposals =
                proposalService.getMyProposals(freelancer);

        return ResponseEntity.ok(proposals);
    }
}