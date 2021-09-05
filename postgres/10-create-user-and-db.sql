-- file: 10-create-user-and-db.sql
CREATE DATABASE graphql;
CREATE ROLE program WITH PASSWORD 'test';
GRANT ALL PRIVILEGES ON DATABASE graphql TO program;
ALTER ROLE program WITH LOGIN;