-- V200 insert states and servers
INSERT INTO states (city, country)
VALUES ('Moscow', 'Russia'),
       ('SPb', 'Russia');

INSERT INTO servers (bandwidth, latency, purpose, state_id)
VALUES (10000, 10, 'BACKEND', 1),
       (5000, 5, 'DATABASE', 1),
       (5000, 5, 'FRONTEND', 1),
       (5000, 5, 'BACKEND', 2);
