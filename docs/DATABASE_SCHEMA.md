# Freelance Marketplace Database Schema

## User

| Column | Type |
|----------|----------|
| id | BIGINT |
| name | VARCHAR |
| email | VARCHAR |
| password | VARCHAR |
| role | ENUM |
| created_at | TIMESTAMP |

---

## FreelancerProfile

| Column | Type |
|----------|----------|
| id | BIGINT |
| user_id | BIGINT |
| title | VARCHAR |
| bio | TEXT |
| skills | TEXT |
| experience_years | INT |
| rating | DOUBLE |

---

## Project

| Column | Type |
|----------|----------|
| id | BIGINT |
| client_id | BIGINT |
| title | VARCHAR |
| description | TEXT |
| budget | DOUBLE |
| status | ENUM |
| created_at | TIMESTAMP |

---

## Proposal

| Column | Type |
|----------|----------|
| id | BIGINT |
| project_id | BIGINT |
| freelancer_id | BIGINT |
| cover_letter | TEXT |
| proposed_budget | DOUBLE |
| status | ENUM |

---

## Review

| Column | Type |
|----------|----------|
| id | BIGINT |
| project_id | BIGINT |
| client_id | BIGINT |
| freelancer_id | BIGINT |
| rating | INT |
| comment | TEXT |