-- file: 10-create-user-and-db.sql
CREATE USER program WITH PASSWORD 'test';
CREATE DATABASE web OWNER program;
