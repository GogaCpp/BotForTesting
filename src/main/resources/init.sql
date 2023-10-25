CREATE SCHEMA IF NOT EXISTS alldata;

CREATE TABLE IF NOT EXISTS alldata.questions (
    id SERIAL PRIMARY KEY,
    question VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS alldata.students (
    id SERIAL PRIMARY KEY,
    studentName VARCHAR(255),
    studentPass VARCHAR(255)
);