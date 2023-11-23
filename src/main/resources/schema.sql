CREATE SCHEMA IF NOT EXISTS allData;

/*---------------блок 1(6 таблиц)-----------*/
/* студенты*/
CREATE TABLE IF NOT EXISTS allData.students (
    id BIGSERIAL PRIMARY KEY,
    studentName TEXT,
    groupName TEXT,
    chat_id BIGINT,
    university_id BIGINT not null
);
/* ответы студентов без типа вопроса*/
CREATE TABLE IF NOT EXISTS allData.students_answers(
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT not null,
    question_id BIGINT not null,
    name TEXT,
    time_start timestamp,
    time_end timestamp,
    test_id BIGINT not null
);

/* ВУЗ*/
CREATE TABLE IF NOT EXISTS allData.universities(
    id BIGSERIAL PRIMARY KEY,
    name TEXT
);
/*Админ*/
CREATE TABLE IF NOT EXISTS allData.admins(
    id BIGSERIAL PRIMARY KEY,
    name TEXT,
    login TEXT,
    pass TEXT,
    university_id BIGINT not null
);
/*Преподаватель*/
CREATE TABLE IF NOT EXISTS allData.teachers(
    id BIGSERIAL PRIMARY KEY,
    name TEXT,
    login TEXT,
    pass TEXT,
    tg_account TEXT,
    university_id BIGINT not null
);
/* Гига админ*/
CREATE TABLE IF NOT EXISTS allData.super_admin(
    id BIGSERIAL PRIMARY KEY,
    login TEXT,
    pass TEXT
);



/*----------------второй блок(связи баз, групп вопросов, дисциплин и тестов)(9 таблиц)----------------------*/



/*вопросы*/
CREATE TABLE IF NOT EXISTS allData.questions (
    id BIGSERIAL PRIMARY KEY,
    name TEXT,
    type TEXT
);
/* ответы*/
CREATE TABLE IF NOT EXISTS allData.answers(
    id BIGSERIAL PRIMARY KEY,
    name TEXT,
    correct BOOLEAN,
    question_id bigint not null
);

/* базы вопросов*/
CREATE TABLE IF NOT EXISTS allData.collections_to_questions(
    id BIGSERIAL PRIMARY KEY,
    question_id BIGINT not null,
    collection_id BIGINT not null
);
/* база */
CREATE TABLE IF NOT EXISTS allData.collections(
    id BIGSERIAL PRIMARY KEY,
    name TEXT UNIQUE
);
/* группы вопросов*/
CREATE TABLE IF NOT EXISTS allData.groups(
    id BIGSERIAL PRIMARY KEY,
    name TEXT UNIQUE,
    collection_id BIGINT not null
);
/* вопросы группы вопросов*/
CREATE TABLE IF NOT EXISTS allData.groups_to_questions(
    id BIGSERIAL PRIMARY KEY,
    group_id BIGINT not null,
    question_id BIGINT not null
);
/* тест */
CREATE TABLE IF NOT EXISTS allData.test(
    id BIGSERIAL PRIMARY KEY,
    collection_id BIGINT not null,
    name TEXT UNIQUE
);

/* дисциплины */
CREATE TABLE IF NOT EXISTS allData.disciplines(
    id BIGSERIAL PRIMARY KEY,
    name TEXT UNIQUE
);
/* многие ко многим дисциплины базы вопросов*/
CREATE TABLE IF NOT EXISTS allData.disciplines_to_collections(
    id BIGSERIAL PRIMARY KEY,
    disciplines_id BIGINT not null,
    collection_id BIGINT not null
);
/*многие ко многим тесты и группы тестов*/
CREATE TABLE IF NOT EXISTS allData.tests_to_groups(
   id BIGSERIAL PRIMARY KEY,
   group_id BIGINT not null,
   test_id BIGINT not null,
   count_questions BIGINT not null
);

/*только для веб части*/
CREATE TABLE IF NOT EXISTS allData.refresh_tokens(
    id BIGSERIAL PRIMARY KEY,
    token TEXT not null,
    login TEXT not null
);