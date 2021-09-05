-- V100 init author and book tables
CREATE TABLE author
(
    id         SERIAL       PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    age        INT,
    experience INT
);

CREATE INDEX idx_author_name ON author (name);

CREATE TABLE book
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    isn        VARCHAR(255) NOT NULL,
    price      INT,
    page_count INT,
    author_id  INT
        CONSTRAINT fk_book_author REFERENCES author (id)
);

CREATE INDEX idx_book_author ON book (author_id);
CREATE INDEX idx_book_name ON book (name);
CREATE UNIQUE INDEX idx_book_isn ON book (isn);