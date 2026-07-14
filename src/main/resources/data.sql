-- Insert constant session types
INSERT INTO session_types (id, name)
VALUES
	(1, 'Yoga'),
	(2, 'Crossfit'),
	(3, 'Pilates'),
	(4, 'Cardio'),
	(5, 'Strength Training'),
	(6, 'Zumba')
ON CONFLICT (id) DO NOTHING;

-- Insert sample coaches
-- Real password for these users: password123
INSERT INTO coaches (id, first_name, last_name, username, password, is_active)
VALUES
	(1, 'John', 'Carter', 'john.carter', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
	(2, 'Anna', 'Baker', 'anna.baker', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE)
ON CONFLICT (username) DO NOTHING;

-- Insert sample trainees
-- Real password for these users: password123
INSERT INTO trainees (id, first_name, last_name, username, password, is_active, birth_date, address)
VALUES
	(3, 'Mike', 'Stone', 'mike.stone', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1994-04-12', 'New York'),
	(4, 'Kate', 'Miller', 'kate.miller', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1998-09-03', 'Chicago'),
	(5, 'Leo', 'Turner', 'leo.turner', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '2001-01-20', 'Los Angeles')
ON CONFLICT (username) DO NOTHING;

-- Coach specializations
INSERT INTO coach_session_type (session_type_id, coach_id)
VALUES
	(1, 1),
	(3, 1),
	(2, 2),
	(5, 2)
ON CONFLICT DO NOTHING;

-- Trainee-coach links
INSERT INTO trainee_coach (trainee_id, coach_id)
VALUES
	(3, 1),
	(4, 1),
	(4, 2),
	(5, 2)
ON CONFLICT DO NOTHING;

-- Sample sessions
INSERT INTO sessions (id, coach_id, trainee_id, session_type_id, name, date, duration)
VALUES
	(1, 1, 3, 1, 'Morning Yoga', '2026-07-10 08:00:00', 60),
	(2, 2, 4, 2, 'HIIT Blast', '2026-07-11 18:30:00', 45)
ON CONFLICT (id) DO NOTHING;

-- Update the sequence to start after the last manually inserted ID
-- This is important if you use GenerationType.SEQUENCE and manually insert IDs
SELECT setval('trainees_id_seq', (SELECT GREATEST(COALESCE((SELECT MAX(id) FROM coaches), 0), COALESCE((SELECT MAX(id) FROM trainees), 0))));
SELECT setval('session_type_id_seq', (SELECT MAX(id) FROM session_types));
SELECT setval('session_id_seq', (SELECT MAX(id) FROM sessions));
