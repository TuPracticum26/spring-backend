-- ====================================================
-- Disable foreign key checks temporarily
-- ====================================================
SET FOREIGN_KEY_CHECKS=0;

-- ====================================================
-- Users

INSERT IGNORE INTO users(id, username, password) VALUES
(1, 'Georgi Goshenkov', '$2y$10$AdCe8GASmz2GcFp7vyTg1.ZRlr7CKGmG2toIa2FOqaSFWoc6aUpcW'),
(2, 'Stefan Stefanov', '$2y$10$1VawqRd2Jsgh2H7DUW14TOUj1Ta9IGZUg3w5q1h5ftuE5cs0K.ZM.'),
(3, 'Dimitur Dimitrov', '$2y$10$nhFAQ8njA76rjAPRFl.4/OHxGPd2IBD1zzISu83mZDWwpNGrLjerm'),
(4, 'Hristo Hristov', '$2y$10$AeWr8tdLImBEkYk5OqLAd.0USzmtb2QgUhvBiwTbhIT7alYiIcSjq'),
(5, 'Misho Mishev', '$2y$10$bItJbBG70MHu.dk4uGEEu.5invMRipc5KNyQVj/.dKBWA9OXSJqyq'),
(6, 'Yordana Yordanova', '$2y$10$x7wWebmiDB.09bsWtvuldePdYmybzOMUHXD4vgkGvTgUqNqAIKmDa'),
(7, 'Tanya Taneva', '$2y$10$uwOsWtfdEqGJzc1.i1B6nuQGXiVTEzVepIQ7UXadESZ/mMhYgKaSy'),
(8, 'Lora Lorova', '$2y$10$lynX82Kch/UtT1n8RXbr9OigGpekN/3oThFDRzMn4qhjnM2b3HxLe'),
(9, 'Plamen Plamenov', '$2y$10$GAXmOfEBjRtQAzDE/iJ1J.lMDT86RcBjEfPXkIpd8OFRGm/jQV0zC'),
(10, 'Pamela Pamelova', '$2y$10$B.CymMO7mTlorJeOq2KlVumrd7xlWiEj1u4aWd759A2L9Z6BtSm.2'),
(11, 'Konstantin Konstantinov', '$2y$10$wQTYRI1J7bqFJ5VgkVnKTOh4hTwZKQP6XrOLhvRurgku3M6WHVHwi');

-- ====================================================
-- User Roles
-- ====================================================
INSERT IGNORE INTO user_roles(user_id, role) VALUES
(1, 'ADMIN'),
(2, 'AUTHOR'),
(3, 'AUTHOR'),
(4, 'AUTHOR'),
(5, 'AUTHOR'),
(6, 'AUTHOR'),
(7, 'REVIEWER'),
(8, 'REVIEWER'),
(9, 'REVIEWER'),
(10, 'READER'),
(11, 'READER');

-- ====================================================
-- Documents
-- ====================================================
INSERT IGNORE INTO documents(id, title, author_id, created_at) VALUES
(1, 'Document 1', 2, '2026-01-26'),
(2, 'Document 2', 3, '2026-01-27'),
(3, 'Document 3', 4, '2026-01-28'),
(4, 'Document 4', 5, '2026-01-29'),
(5, 'Document 5', 6, '2026-01-30'),
(6, 'Document 6', 6, '2026-01-31'),
(7, 'Document 7', 5, '2026-02-01'),
(8, 'Document 8', 4, '2026-02-02'),
(9, 'Document 9', 3, '2026-02-03'),
(10, 'Document 10', 2, '2026-02-04');

-- ====================================================
-- Document Versions
-- ====================================================
INSERT IGNORE INTO document_versions(id, document_id, version_number, content, status, created_by_id, created_at) VALUES
(1, 1, 1, 'First draft of Q1 financial report', 'DRAFT', 1, '2026-01-10 09:00:00'),
(2, 1, 2, 'Updated financial report with corrected revenue figures', 'PENDING', 2, '2026-01-12 10:30:00'),
(3, 1, 3, 'Final Q1 financial report approved by finance', 'APPROVED', 3, '2026-01-15 14:00:00'),
(4, 2, 1, 'Initial project proposal for new CRM system', 'DRAFT', 4, '2026-01-11 08:00:00'),
(5, 2, 2, 'Revised CRM proposal with budget breakdown', 'PENDING', 5, '2026-01-13 11:00:00'),
(6, 2, 3, 'CRM proposal after stakeholder feedback', 'REJECTED', 6, '2026-01-16 09:30:00'),
(7, 2, 4, 'Final CRM proposal v4', 'APPROVED', 7, '2026-01-18 13:00:00'),
(8, 3, 1, 'Employee handbook draft - remote work policy', 'DRAFT', 8, '2026-01-12 09:00:00'),
(9, 3, 2, 'Updated handbook including hybrid work guidelines', 'PENDING', 9, '2026-01-14 10:00:00'),
(10, 3, 3, 'HR approved employee handbook 2026', 'APPROVED', 10, '2026-01-17 16:00:00'),
(11, 4, 1, 'Marketing strategy for product launch', 'DRAFT', 1, '2026-01-13 08:30:00'),
(12, 4, 2, 'Marketing strategy with social media plan', 'PENDING', 2, '2026-01-15 11:30:00'),
(13, 4, 3, 'Revised marketing strategy after budget cuts', 'REJECTED', 3, '2026-01-18 09:00:00'),
(14, 4, 4, 'Final lean marketing strategy', 'APPROVED', 4, '2026-01-20 14:00:00'),
(15, 5, 1, 'Security audit report - initial findings', 'DRAFT', 5, '2026-01-14 10:00:00'),
(16, 5, 2, 'Security audit with vulnerability fixes', 'PENDING', 6, '2026-01-16 13:00:00'),
(17, 5, 3, 'Security audit final approved version', 'APPROVED', 7, '2026-01-19 11:00:00'),
(18, 6, 1, 'Q2 sales forecast draft', 'DRAFT', 8, '2026-01-15 09:00:00'),
(19, 6, 2, 'Q2 sales forecast with regional breakdown', 'PENDING', 9, '2026-01-17 10:00:00'),
(20, 6, 3, 'Q2 sales forecast - executive summary', 'REJECTED', 10, '2026-01-20 09:30:00'),
(21, 6, 4, 'Final Q2 sales forecast approved', 'APPROVED', 1, '2026-01-22 15:00:00'),
(22, 7, 1, 'Customer support playbook draft', 'DRAFT', 2, '2026-01-16 08:00:00'),
(23, 7, 2, 'Updated playbook with escalation procedures', 'PENDING', 3, '2026-01-18 11:00:00'),
(24, 7, 3, 'Support playbook final version', 'APPROVED', 4, '2026-01-21 13:30:00'),
(25, 8, 1, 'Onboarding checklist for new hires', 'DRAFT', 5, '2026-01-17 09:00:00'),
(26, 8, 2, 'Onboarding checklist with IT and HR tasks', 'PENDING', 6, '2026-01-19 10:30:00'),
(27, 8, 3, 'Revised onboarding checklist after team feedback', 'REJECTED', 7, '2026-01-22 14:00:00'),
(28, 8, 4, 'Final onboarding checklist 2026', 'APPROVED', 8, '2026-01-24 09:00:00'),
(29, 9, 1, 'Annual compliance review part 1', 'DRAFT', 9, '2026-01-18 08:00:00'),
(30, 9, 2, 'Compliance review with GDPR updates', 'PENDING', 10, '2026-01-20 11:00:00'),
(31, 9, 3, 'Compliance review approved by legal', 'APPROVED', 1, '2026-01-23 16:00:00'),
(32, 10, 1, 'Server migration plan initial', 'DRAFT', 2, '2026-01-19 09:00:00'),
(33, 10, 2, 'Migration plan with rollback strategy', 'PENDING', 3, '2026-01-21 10:00:00'),
(34, 10, 3, 'Updated migration plan - phase 1 only', 'REJECTED', 4, '2026-01-24 13:00:00'),
(35, 10, 4, 'Final server migration plan Q2', 'APPROVED', 5, '2026-01-26 14:30:00'),
(36, 1, 4, 'Q1 report addendum - corrected charts', 'DRAFT', 6, '2026-01-20 09:00:00'),
(37, 1, 5, 'Q1 report final with all corrections', 'APPROVED', 7, '2026-01-23 11:00:00'),
(38, 2, 5, 'CRM implementation timeline v5', 'DRAFT', 8, '2026-01-21 08:30:00'),
(39, 2, 6, 'CRM rollout plan with training schedule', 'PENDING', 9, '2026-01-24 10:00:00'),
(40, 2, 7, 'Final CRM project plan', 'APPROVED', 10, '2026-01-27 13:00:00'),
(41, 3, 4, 'Remote work equipment policy', 'DRAFT', 1, '2026-01-22 09:00:00'),
(42, 3, 5, 'Updated equipment policy with budget limits', 'PENDING', 2, '2026-01-25 14:00:00'),
(43, 3, 6, 'Final remote work policy', 'APPROVED', 3, '2026-01-28 10:00:00'),
(44, 4, 5, 'Launch event planning document', 'DRAFT', 4, '2026-01-23 08:00:00'),
(45, 4, 6, 'Event plan with vendor contracts', 'PENDING', 5, '2026-01-26 11:00:00'),
(46, 4, 7, 'Final launch event strategy', 'APPROVED', 6, '2026-01-29 15:00:00'),
(47, 5, 4, 'Security remediation roadmap', 'DRAFT', 7, '2026-01-24 09:30:00'),
(48, 5, 5, 'Remediation plan with deadlines', 'PENDING', 8, '2026-01-27 10:00:00'),
(49, 5, 6, 'Final security roadmap Q2-Q3', 'APPROVED', 9, '2026-01-30 13:00:00'),
(50, 6, 5, 'Sales forecast by product line', 'DRAFT', 10, '2026-01-25 08:00:00'),
(51, 6, 6, 'Updated sales forecast with new product launch', 'PENDING', 1, '2026-01-28 14:00:00'),
(52, 6, 7, 'Final Q2 sales forecast v7', 'APPROVED', 2, '2026-01-31 11:30:00'),
(53, 7, 4, 'Customer escalation matrix', 'DRAFT', 3, '2026-01-26 09:00:00'),
(54, 7, 5, 'Escalation matrix with SLAs', 'PENDING', 4, '2026-01-29 10:00:00'),
(55, 7, 6, 'Final customer support escalation policy', 'APPROVED', 5, '2026-02-01 16:00:00'),
(56, 8, 5, 'New hire training schedule', 'DRAFT', 6, '2026-01-27 08:30:00'),
(57, 8, 6, 'Training schedule with department sessions', 'PENDING', 7, '2026-01-30 11:00:00'),
(58, 8, 7, 'Final onboarding training plan', 'APPROVED', 8, '2026-02-02 13:00:00'),
(59, 9, 4, 'Data retention policy draft', 'DRAFT', 9, '2026-01-28 09:00:00'),
(60, 9, 5, 'Data retention with legal review', 'PENDING', 10, '2026-01-31 10:30:00'),
(61, 9, 6, 'Final data retention policy', 'APPROVED', 1, '2026-02-03 14:00:00'),
(62, 10, 5, 'Post-migration validation plan', 'DRAFT', 2, '2026-01-29 08:00:00'),
(63, 10, 6, 'Validation plan with testing steps', 'PENDING', 3, '2026-02-01 11:00:00'),
(64, 10, 7, 'Final migration validation protocol', 'APPROVED', 4, '2026-02-04 15:30:00'),
(65, 1, 6, 'Q1 report executive summary', 'DRAFT', 5, '2026-01-30 09:00:00'),
(66, 1, 7, 'Executive summary with board notes', 'PENDING', 6, '2026-02-02 10:00:00'),
(67, 1, 8, 'Final Q1 executive summary', 'APPROVED', 7, '2026-02-05 13:00:00'),
(68, 2, 8, 'CRM risk assessment', 'DRAFT', 8, '2026-01-31 08:30:00'),
(69, 2, 9, 'Risk assessment with mitigation plan', 'PENDING', 9, '2026-02-03 14:00:00'),
(70, 2, 10, 'Final CRM risk register', 'APPROVED', 10, '2026-02-06 11:00:00'),
(71, 3, 7, 'Benefits update for remote employees', 'DRAFT', 1, '2026-02-01 09:00:00'),
(72, 3, 8, 'Benefits package revised', 'PENDING', 2, '2026-02-04 10:30:00'),
(73, 3, 9, 'Final remote benefits policy', 'APPROVED', 3, '2026-02-07 16:00:00'),
(74, 4, 8, 'Post-launch survey design', 'DRAFT', 4, '2026-02-02 08:00:00'),
(75, 4, 9, 'Survey with customer feedback metrics', 'PENDING', 5, '2026-02-05 11:00:00'),
(76, 4, 10, 'Final post-launch feedback plan', 'APPROVED', 6, '2026-02-08 13:30:00'),
(77, 5, 7, 'Security awareness training content', 'DRAFT', 7, '2026-02-03 09:00:00'),
(78, 5, 8, 'Training modules for all staff', 'PENDING', 8, '2026-02-06 10:00:00'),
(79, 5, 9, 'Final security training program', 'APPROVED', 9, '2026-02-09 14:00:00'),
(80, 6, 8, 'Sales performance metrics dashboard', 'DRAFT', 10, '2026-02-04 08:30:00'),
(81, 6, 9, 'Dashboard with real-time KPIs', 'PENDING', 1, '2026-02-07 13:00:00'),
(82, 6, 10, 'Final sales dashboard Q2', 'APPROVED', 2, '2026-02-10 11:00:00'),
(83, 7, 7, 'Customer satisfaction survey template', 'DRAFT', 3, '2026-02-05 09:00:00'),
(84, 7, 8, 'Survey with NPS and CSAT questions', 'PENDING', 4, '2026-02-08 10:30:00'),
(85, 7, 9, 'Final customer feedback survey', 'APPROVED', 5, '2026-02-11 15:00:00'),
(86, 8, 8, 'Mentorship program guidelines', 'DRAFT', 6, '2026-02-06 08:00:00'),
(87, 8, 9, 'Mentorship program with pairings', 'PENDING', 7, '2026-02-09 11:00:00'),
(88, 8, 10, 'Final mentorship program handbook', 'APPROVED', 8, '2026-02-12 14:00:00'),
(89, 9, 7, 'Vendor security assessment', 'DRAFT', 9, '2026-02-07 09:30:00'),
(90, 9, 8, 'Vendor assessment with compliance scores', 'PENDING', 10, '2026-02-10 10:00:00'),
(91, 9, 9, 'Final vendor security framework', 'APPROVED', 1, '2026-02-13 13:00:00'),
(92, 10, 8, 'Cloud cost optimization plan', 'DRAFT', 2, '2026-02-08 08:00:00'),
(93, 10, 9, 'Cost optimization with reserved instances', 'PENDING', 3, '2026-02-11 14:30:00'),
(94, 10, 10, 'Final cloud cost reduction strategy', 'APPROVED', 4, '2026-02-14 11:00:00'),
(95, 5, 10, 'Security incident response playbook', 'DRAFT', 5, '2026-02-09 09:00:00'),
(96, 5, 11, 'Incident response with communication plan', 'PENDING', 6, '2026-02-12 10:00:00'),
(97, 5, 12, 'Final security incident response', 'APPROVED', 7, '2026-02-15 16:00:00'),
(98, 3, 10, 'Performance review template', 'DRAFT', 8, '2026-02-10 08:30:00'),
(99, 3, 11, 'Review template with self-assessment', 'PENDING', 9, '2026-02-13 11:30:00'),
(100, 3, 12, 'Final annual performance review form', 'APPROVED', 10, '2026-02-16 14:00:00');

-- ====================================================
-- version_comments table
-- ====================================================
INSERT IGNORE INTO version_comments(version_id, comment) VALUES
(1, 'Initial draft created for review'),
(2, 'Updated introduction section'),
(2, 'Fixed formatting issues'),
(3, 'First version submitted'),
(4, 'All sections reviewed and approved'),
(4, 'Compliance check passed'),
(5, 'Draft pending team review'),
(6, 'Rejected due to missing references'),
(6, 'Content does not meet style guidelines'),
(7, 'Awaiting manager sign-off'),
(8, 'Pending legal review'),
(9, 'Approved after final revision'),
(10, 'Work in progress'),
(11, 'Under review by senior editor'),
(12, 'Approved with minor notes'),
(12, 'Archived for compliance records'),
(13, 'Initial draft, not yet reviewed');

-- ====================================================
-- Re-enable foreign key checks
-- ====================================================
SET FOREIGN_KEY_CHECKS=1;