-- V100 create state and servers tables
CREATE TABLE states
(
    id      SERIAL PRIMARY KEY,
    city    VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL
);

CREATE TABLE servers
(
    id        SERIAL PRIMARY KEY,
    address   VARCHAR(255),
    purpose   VARCHAR(20) NOT NULL,
    latency   INT         NOT NULL,
    bandwidth INT         NOT NULL,
    state_id  INT         NOT NULL
        CONSTRAINT fk_servers_state_id REFERENCES states (id)
);

CREATE INDEX idx_servers_state_id ON servers (state_id);