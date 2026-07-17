# Entity Relationship Diagram

User
│
├── FreelancerProfile
│
├── Project
│
└── Review

Project
│
├── Proposal
│
└── Review

Relationships:

1 User -> 1 FreelancerProfile

1 User -> Many Projects

1 Project -> Many Proposals

1 Project -> 1 Review