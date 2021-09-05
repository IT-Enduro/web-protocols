-- V200 insert author and books
INSERT INTO author(id, name, age, experience)
VALUES (1, 'Alex', 31, 11);

INSERT INTO book(name, isn, price, page_count, author_id)
VALUES ('New Age', gen_random_uuid(), 1000, 256, 1);
INSERT INTO book(name, isn, price, page_count, author_id)
VALUES ('Lego Technic: full definitive guide', gen_random_uuid(), 2000, 512, 1);