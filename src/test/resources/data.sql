INSERT INTO company(uuid, name) VALUES
('company-uuid-1', 'company-1');

INSERT INTO computer(uuid, name, company_uuid) VALUES
('computer-uuid-1', 'computer-1', 'company-uuid-1'),
('computer-uuid-2', 'computer-2', 'company-uuid-1');