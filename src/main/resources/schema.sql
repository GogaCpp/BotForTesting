CREATE SCHEMA IF NOT EXISTS alldata;

CREATE TABLE IF NOT EXISTS alldata.questions (
    id BIGSERIAL PRIMARY KEY,
    question TEXT
);

CREATE TABLE IF NOT EXISTS alldata.students (
    id BIGSERIAL PRIMARY KEY,
    studentname TEXT,
    studentpass TEXT
);