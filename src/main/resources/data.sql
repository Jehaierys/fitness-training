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
-- Password for all users: password123
INSERT INTO coaches (id, first_name, last_name, username, password, is_active)
VALUES
    (1, 'John', 'Carter', 'john.carter', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (2, 'Anna', 'Baker', 'anna.baker', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (3, 'David', 'Wilson', 'david.wilson', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (4, 'Emily', 'Roberts', 'emily.roberts', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (5, 'James', 'Anderson', 'james.anderson', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (6, 'Sophia', 'Walker', 'sophia.walker', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (7, 'Michael', 'Harris', 'michael.harris', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (8, 'Olivia', 'Young', 'olivia.young', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE)
ON CONFLICT (username) DO NOTHING;

-- Insert additional coaches
-- Password for all users: password123
INSERT INTO coaches (id, first_name, last_name, username, password, is_active)
VALUES
    (21, 'Benjamin', 'Clark', 'benjamin.clark', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (22, 'Victoria', 'Lewis', 'victoria.lewis', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (23, 'Matthew', 'Hill', 'matthew.hill', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (24, 'Natalie', 'Campbell', 'natalie.campbell', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (25, 'Andrew', 'Mitchell', 'andrew.mitchell', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (26, 'Samantha', 'Perez', 'samantha.perez', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (27, 'Christopher', 'Robinson', 'christopher.robinson', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (28, 'Rachel', 'Turner', 'rachel.turner', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (29, 'Kevin', 'Phillips', 'kevin.phillips', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE),
    (30, 'Lauren', 'Parker', 'lauren.parker', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE)
ON CONFLICT (username) DO NOTHING;


-- Insert sample trainees
-- Password for all users: password123
INSERT INTO trainees (id, first_name, last_name, username, password, is_active, birth_date, address)
VALUES
    (9,  'Mike', 'Stone', 'mike.stone', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1994-04-12', 'New York'),
    (10, 'Kate', 'Miller', 'kate.miller', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1998-09-03', 'Chicago'),
    (11, 'Leo', 'Turner', 'leo.turner', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '2001-01-20', 'Los Angeles'),
    (12, 'Emma', 'Hall', 'emma.hall', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1996-02-15', 'Boston'),
    (13, 'Daniel', 'Scott', 'daniel.scott', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1992-11-08', 'Seattle'),
    (14, 'Grace', 'Evans', 'grace.evans', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1999-07-19', 'Denver'),
    (15, 'Ryan', 'King', 'ryan.king', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1995-05-27', 'Houston'),
    (16, 'Chloe', 'Green', 'chloe.green', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '2000-10-01', 'San Francisco'),
    (17, 'Noah', 'Adams', 'noah.adams', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1997-12-14', 'Phoenix'),
    (18, 'Lily', 'Nelson', 'lily.nelson', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1993-08-30', 'Miami'),
    (19, 'Ethan', 'Moore', 'ethan.moore', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '2002-03-11', 'Dallas'),
    (20, 'Ava', 'Taylor', 'ava.taylor', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1991-06-24', 'Austin')
ON CONFLICT (username) DO NOTHING;

-- Insert additional trainees
-- Password for all users: password123
INSERT INTO trainees (id, first_name, last_name, username, password, is_active, birth_date, address)
VALUES
    (31, 'Nathan', 'Ward', 'nathan.ward', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1995-01-15', 'Philadelphia'),
    (32, 'Zoe', 'Brooks', 'zoe.brooks', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1999-04-28', 'Portland'),
    (33, 'Jacob', 'Reed', 'jacob.reed', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1993-09-10', 'Las Vegas'),
    (34, 'Hannah', 'Cook', 'hannah.cook', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '2000-07-22', 'Nashville'),
    (35, 'Logan', 'Morgan', 'logan.morgan', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1998-12-03', 'Charlotte'),
    (36, 'Mia', 'Bell', 'mia.bell', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '2001-06-14', 'Orlando'),
    (37, 'Jack', 'Bailey', 'jack.bailey', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1996-11-19', 'Detroit'),
    (38, 'Ella', 'Rivera', 'ella.rivera', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1997-03-08', 'San Diego'),
    (39, 'Luke', 'Cooper', 'luke.cooper', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '1994-08-26', 'Columbus'),
    (40, 'Aria', 'Richardson', 'aria.richardson', '$2y$05$oR.R.eta2PvUrUFNRGeufusz7TyBpVmsDi.OxsbmjLeHZY4Nslh0.', TRUE, '2002-02-17', 'Indianapolis')
ON CONFLICT (username) DO NOTHING;



-- Coach specializations
INSERT INTO coach_session_type (session_type_id, coach_id)
VALUES
    -- Existing coaches
    (1, 1),
    (3, 1),

    (2, 2),
    (5, 2),

    (1, 3),
    (2, 3),
    (4, 3),

    (4, 4),

    (3, 5),
    (5, 5),

    -- Coach 6 intentionally has no specialization

    (2, 7),

    (1, 8),
    (4, 8),
    (5, 8),

    (3, 21),

    (1, 22),
    (2, 22),

    -- Coach 23 intentionally has no specialization

    (5, 24),

    (2, 25),
    (3, 25),

    (1, 26),

    (4, 27),
    (5, 27),

    -- Coach 28 intentionally has no specialization

    (2, 29),
    (3, 29),
    (5, 29),

    (1, 30)
ON CONFLICT DO NOTHING;


-- Trainee-coach links
INSERT INTO trainee_coach (trainee_id, coach_id)
VALUES
    -- Existing trainees
    (9, 1),
    (9, 3),

    (10, 1),
    (10, 2),

    (11, 2),

    (12, 3),
    (12, 5),

    (13, 4),

    (14, 1),
    (14, 7),

    (15, 8),

    (16, 21),
    (16, 22),

    (17, 22),

    (18, 24),
    (18, 25),

    (19, 25),
    (19, 29),

    (20, 30),

    (31, 3),
    (31, 8),

    (32, 26),

    (33, 27),
    (33, 29),

    -- trainee 34 intentionally has no coach

    (35, 24),

    (36, 1),
    (36, 21),
    (36, 30),

    -- trainee 37 intentionally has no coach

    (38, 5),

    (39, 2),
    (39, 25)

-- trainee 40 intentionally has no coach
ON CONFLICT DO NOTHING;



-- Sample sessions
INSERT INTO sessions (id, coach_id, trainee_id, session_type_id, name, date, duration)
VALUES
    (1, 1, 9, 1, 'Morning Yoga',          '2026-07-10 08:00:00', 60),
    (2, 3, 9, 4, 'Strength Basics',       '2026-07-10 17:30:00', 75),
    (3, 1, 10, 3, 'Cardio Burn',          '2026-07-11 09:00:00', 45),
    (4, 2, 10, 2, 'HIIT Blast',           '2026-07-11 18:30:00', 45),
    (5, 2, 11, 5, 'Boxing Fundamentals',  '2026-07-12 19:00:00', 60),
    (6, 3, 12, 2, 'Endurance Training',   '2026-07-12 10:00:00', 90),
    (7, 5, 12, 5, 'Kickboxing Intro',     '2026-07-13 18:00:00', 60),
    (8, 4, 13, 4, 'Strength Circuit',     '2026-07-13 11:00:00', 75),
    (9, 1, 14, 1, 'Yoga Flow',            '2026-07-14 08:30:00', 60),
    (10, 7, 14, 2, 'Cross Training',      '2026-07-14 18:00:00', 60),

    (11, 8, 15, 5, 'Advanced Boxing',     '2026-07-15 19:00:00', 75),
    (12, 21, 16, 3, 'Core Workout',       '2026-07-15 09:00:00', 45),
    (13, 22, 16, 2, 'Interval Running',   '2026-07-16 18:30:00', 60),
    (14, 22, 17, 1, 'Morning Stretch',    '2026-07-16 08:00:00', 45),
    (15, 24, 18, 5, 'Power Boxing',       '2026-07-17 19:30:00', 60),
    (16, 25, 18, 2, 'HIIT Challenge',     '2026-07-17 17:00:00', 50),
    (17, 25, 19, 3, 'Cardio Express',     '2026-07-18 10:30:00', 45),
    (18, 29, 19, 5, 'Sparring Session',   '2026-07-18 19:00:00', 90),
    (19, 30, 20, 1, 'Sunrise Yoga',       '2026-07-19 07:30:00', 60),
    (20, 3, 31, 2, 'Functional Fitness',  '2026-07-19 18:00:00', 75),

    (21, 8, 31, 4, 'Strength Builder',    '2026-07-20 17:00:00', 60),
    (22, 26, 32, 1, 'Mobility Session',   '2026-07-20 08:00:00', 45),
    (23, 27, 33, 4, 'Leg Day',            '2026-07-21 18:00:00', 70),
    (24, 29, 33, 5, 'Combat Conditioning','2026-07-21 19:30:00', 80),
    (25, 24, 35, 5, 'Heavy Bag Workout',  '2026-07-22 18:30:00', 60),
    (26, 1, 36, 3, 'Fat Burn Cardio',     '2026-07-22 09:00:00', 45),
    (27, 21, 36, 3, 'Core Stability',     '2026-07-23 17:30:00', 60),
    (28, 30, 36, 1, 'Evening Yoga',       '2026-07-23 20:00:00', 60),
    (29, 5, 38, 3, 'Cardio Mix',          '2026-07-24 10:00:00', 50),
    (30, 2, 39, 2, 'Explosive HIIT',      '2026-07-24 18:30:00', 45),

    (31, 25, 39, 2, 'Athletic Conditioning','2026-07-25 17:30:00', 75),

    (32, 1, 9, 1, 'Weekend Yoga',         '2026-07-26 09:00:00', 60),
    (33, 2, 10, 5, 'Box Fit',             '2026-07-26 18:00:00', 60),
    (34, 3, 12, 1, 'Recovery Stretch',    '2026-07-27 08:30:00', 45),
    (35, 8, 15, 4, 'Upper Body Strength', '2026-07-27 19:00:00', 75),
    (36, 22, 16, 1, 'Sunrise Mobility',   '2026-07-28 07:30:00', 45),
    (37, 24, 18, 5, 'Boxing Drills',      '2026-07-28 18:30:00', 60),
    (38, 29, 19, 3, 'Conditioning Run',   '2026-07-29 09:00:00', 60),
    (39, 3, 31, 4, 'Full Body Workout',   '2026-07-29 18:00:00', 90),
    (40, 30, 20, 1, 'Relax Yoga',         '2026-07-30 20:00:00', 60)
ON CONFLICT (id) DO NOTHING;



-- Update the sequence to start after the last manually inserted ID
-- This is important if you use GenerationType.SEQUENCE and manually insert IDs
SELECT setval('users_id_seq', (SELECT GREATEST(COALESCE((SELECT MAX(id) FROM coaches), 0), COALESCE((SELECT MAX(id) FROM trainees), 0))));
SELECT setval('session_type_id_seq', (SELECT MAX(id) FROM session_types));
SELECT setval('session_id_seq', (SELECT MAX(id) FROM sessions));
