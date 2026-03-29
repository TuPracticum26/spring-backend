INSERT INTO users(username, password, role) VALUES
                                               ('Georgi Goshenkov', 'Gosho1!', 'Admin'),
                                               ('Stefan Stefanov', 'Stefan1!', 'Author'),
                                               ('Dimitur Dimitrov', 'Dimitur1!', 'Author'),
                                               ('Hristo Hristov', 'Hristo1!', 'Author'),
                                               ('Misho Mishev', 'Misho1!', 'Author'),
                                               ('Yordana Yordanova', 'Yordana1!', 'Author'),
                                               ('Tanya Taneva', 'Tanya1!', 'Reviewer'),
                                               ('Lora Lorova', 'Lora1!', 'Reviewer'),
                                               ('Plamen Plamenov', 'Plamen1!', 'Reviewer'),
                                               ('Pamela Pamelova', 'Pamela1!', 'Reader'),
                                               ('Konstantin Konstantinov', 'Konstantin1!', 'Reader');

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