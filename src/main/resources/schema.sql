CREATE SCHEMA IF NOT EXISTS allData;

CREATE TABLE IF NOT EXISTS allData.questions (
    id BIGSERIAL PRIMARY KEY,
    question TEXT
);
CREATE TABLE IF NOT EXISTS allData.answers(
    id BIGSERIAL PRIMARY KEY,
    answer TEXT,
    correct BOOLEAN,
    idQuestion bigint
);

CREATE TABLE IF NOT EXISTS allData.students (
    id BIGSERIAL PRIMARY KEY,
    studentName TEXT,
    studentPass TEXT,
    chat_id BIGINT
);