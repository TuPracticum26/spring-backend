INSERT INTO users(username, password, role) VALUES
('Georgi Goshenkov', '$2y$10$AdCe8GASmz2GcFp7vyTg1.ZRlr7CKGmG2toIa2FOqaSFWoc6aUpcW', '["Admin"]'),
('Stefan Stefanov', '$2y$10$1VawqRd2Jsgh2H7DUW14TOUj1Ta9IGZUg3w5q1h5ftuE5cs0K.ZM.', '["Author"]'),
('Dimitur Dimitrov', '$2y$10$nhFAQ8njA76rjAPRFl.4/OHxGPd2IBD1zzISu83mZDWwpNGrLjerm', '["Author"]'),
('Hristo Hristov', '$2y$10$AeWr8tdLImBEkYk5OqLAd.0USzmtb2QgUhvBiwTbhIT7alYiIcSjq', '["Author"]'),
('Misho Mishev', '$2y$10$bItJbBG70MHu.dk4uGEEu.5invMRipc5KNyQVj/.dKBWA9OXSJqyq', '["Author"]'),
('Yordana Yordanova', '$2y$10$x7wWebmiDB.09bsWtvuldePdYmybzOMUHXD4vgkGvTgUqNqAIKmDa', '["Author"]'),
('Tanya Taneva', '$2y$10$uwOsWtfdEqGJzc1.i1B6nuQGXiVTEzVepIQ7UXadESZ/mMhYgKaSy', '["Reviewer"]'),
('Lora Lorova', '$2y$10$lynX82Kch/UtT1n8RXbr9OigGpekN/3oThFDRzMn4qhjnM2b3HxLe', '["Reviewer"]'),
('Plamen Plamenov', '$2y$10$GAXmOfEBjRtQAzDE/iJ1J.lMDT86RcBjEfPXkIpd8OFRGm/jQV0zC', '["Reviewer"]'),
('Pamela Pamelova', '$2y$10$B.CymMO7mTlorJeOq2KlVumrd7xlWiEj1u4aWd759A2L9Z6BtSm.2', '["Reader"]'),
('Konstantin Konstantinov', '$2y$10$wQTYRI1J7bqFJ5VgkVnKTOh4hTwZKQP6XrOLhvRurgku3M6WHVHwi', '["Reader"]');

INSERT INTO documents(title, author_id, created_at) VALUES
('Document 1', 2, '2026-01-26'),
('Document 2', 3, '2026-01-27'),
('Document 3', 4, '2026-01-28'),
('Document 4', 5, '2026-01-29'),
('Document 5', 6, '2026-01-30'),
('Document 6', 6, '2026-01-31'),
('Document 7', 5, '2026-02-01'),
('Document 8', 4, '2026-02-02'),
('Document 9', 3, '2026-02-03'),
('Document 10', 2, '2026-02-04');

INSERT INTO document_versions(document_id, version_number, content, status, created_by_id, created_at) VALUES
(1, 1, 'Initial content for document 1', 'Draft', 2, '2026-01-26 09:00:00'),
(1, 2, 'Revised content for document 1', 'Pending', 2, '2026-01-27 10:00:00'),
(2, 1, 'Initial content for document 2', 'Draft', 3, '2026-01-27 09:00:00'),
(2, 2, 'Revised content for document 2', 'Approved', 3, '2026-01-28 11:00:00'),
(3, 1, 'Initial content for document 3', 'Draft', 4, '2026-01-28 09:00:00'),
(3, 2, 'Revised content for document 3', 'Rejected', 4, '2026-01-29 14:00:00'),
(4, 1, 'Initial content for document 4', 'Draft', 5, '2026-01-29 09:00:00'),
(5, 1, 'Initial content for document 5', 'Pending', 6, '2026-01-30 09:00:00'),
(6, 1, 'Initial content for document 6', 'Approved', 6, '2026-01-31 09:00:00'),
(7, 1, 'Initial content for document 7', 'Draft', 5, '2026-02-01 09:00:00'),
(8, 1, 'Initial content for document 8', 'Pending', 4, '2026-02-02 09:00:00'),
(9, 1, 'Initial content for document 9', 'Approved', 3, '2026-02-03 09:00:00'),
(10, 1, 'Initial content for document 10', 'Draft', 2, '2026-02-04 09:00:00');