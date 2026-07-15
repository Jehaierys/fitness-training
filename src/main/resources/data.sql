-- Insert constant session types
INSERT INTO session_types (id, name) VALUES (1, 'Yoga') ON CONFLICT (id) DO NOTHING;
INSERT INTO session_types (id, name) VALUES (2, 'Crossfit') ON CONFLICT (id) DO NOTHING;
INSERT INTO session_types (id, name) VALUES (3, 'Pilates') ON CONFLICT (id) DO NOTHING;
INSERT INTO session_types (id, name) VALUES (4, 'Cardio') ON CONFLICT (id) DO NOTHING;
INSERT INTO session_types (id, name) VALUES (5, 'Strength Training') ON CONFLICT (id) DO NOTHING;
INSERT INTO session_types (id, name) VALUES (6, 'Zumba') ON CONFLICT (id) DO NOTHING;

-- Update the sequence to start after the last manually inserted ID
-- This is important if you use GenerationType.SEQUENCE and manually insert IDs
SELECT setval('session_type_id_seq', (SELECT MAX(id) FROM session_types));
