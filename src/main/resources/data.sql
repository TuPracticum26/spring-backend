-- ====================================================
-- Disable foreign key checks temporarily
-- ====================================================
SET FOREIGN_KEY_CHECKS=0;

-- ====================================================
-- Users

INSERT INTO users(id, username, password) VALUES
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
INSERT INTO user_roles(user_id, role) VALUES
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
INSERT INTO documents(id, title, author_id, created_at) VALUES
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
INSERT INTO document_versions(id, document_id, version_number, content, status, created_by_id, created_at) VALUES
(1, 1, 1, 'Initial content for document 1', 'DRAFT', 2, '2026-01-26 09:00:00'),
(2, 1, 2, 'Revised content for document 1', 'PENDING', 2, '2026-01-27 10:00:00'),
(3, 2, 1, 'Initial content for document 2', 'DRAFT', 3, '2026-01-27 09:00:00'),
(4, 2, 2, 'Revised content for document 2', 'APPROVED', 3, '2026-01-28 11:00:00'),
(5, 3, 1, 'Initial content for document 3', 'DRAFT', 4, '2026-01-28 09:00:00'),
(6, 3, 2, 'Revised content for document 3', 'REJECTED', 4, '2026-01-29 14:00:00'),
(7, 4, 1, 'Initial content for document 4', 'DRAFT', 5, '2026-01-29 09:00:00'),
(8, 5, 1, 'Initial content for document 5', 'PENDING', 6, '2026-01-30 09:00:00'),
(9, 6, 1, 'Initial content for document 6', 'APPROVED', 6, '2026-01-31 09:00:00'),
(10, 7, 1, 'Initial content for document 7', 'DRAFT', 5, '2026-02-01 09:00:00'),
(11, 8, 1, 'Initial content for document 8', 'PENDING', 4, '2026-02-02 09:00:00'),
(12, 9, 1, 'Initial content for document 9', 'APPROVED', 3, '2026-02-03 09:00:00'),
(13, 10, 1, 'Initial content for document 10', 'DRAFT', 2, '2026-02-04 09:00:00');

-- ====================================================
-- version_comments table
-- ====================================================
INSERT INTO version_comments(version_id, comment) VALUES
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