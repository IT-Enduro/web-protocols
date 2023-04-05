-- file: 10-create-user-and-db.sql
CREATE DATABASE web;
CREATE USER program WITH PASSWORD 'test';
GRANT ALL PRIVILEGES ON DATABASE web TO program;