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
   answer_id BIGINT not null,
   time_start timestamp,
   time_end timestamp,
   test_id BIGINT not null
);

/* ВУЗ*/
CREATE TABLE IF NOT EXISTS allData.universities(
   id BIGSERIAL PRIMARY KEY,
   name TEXT UNIQUE
);
/*Админ*/
CREATE TABLE IF NOT EXISTS allData.admins(
     id BIGSERIAL PRIMARY KEY,
     name TEXT UNIQUE,
     login TEXT UNIQUE,
     pass TEXT,
     university_id BIGINT not null
);
/*Преподаватель*/
CREATE TABLE IF NOT EXISTS allData.teachers(
   id BIGSERIAL PRIMARY KEY,
   name TEXT,
   login TEXT UNIQUE,
   pass TEXT,
   tg_account TEXT,
   university_id BIGINT not null
);
/* Гига админ*/
CREATE TABLE IF NOT EXISTS allData.super_admins(
      id BIGSERIAL PRIMARY KEY,
      login TEXT UNIQUE,
      pass TEXT
);



/*----------------второй блок(связи баз, групп вопросов, дисциплин и тестов)(9 таблиц)----------------------*/



/*вопросы*/
CREATE TABLE IF NOT EXISTS allData.questions (
    id BIGSERIAL PRIMARY KEY,
    question TEXT,
    type TEXT,
    is_valid BOOLEAN
);
/* ответы*/
CREATE TABLE IF NOT EXISTS allData.answers(
      id BIGSERIAL PRIMARY KEY,
      answer TEXT,
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
    discipline_id BIGINT not null,
    collection_id BIGINT not null
);
/*многие ко многим тесты и группы тестов*/
CREATE TABLE IF NOT EXISTS allData.tests_to_groups(
    id BIGSERIAL PRIMARY KEY,
    group_id BIGINT not null,
    test_id BIGINT not null,
    count_questions BIGINT not null
);

CREATE TABLE IF NOT EXISTS allData.refresh_tokens(
      id BIGSERIAL PRIMARY KEY,
      token TEXT not null,
      login TEXT not null
);

CREATE OR REPLACE Procedure allData.init()
AS '
DECLARE
    n integer;
BEGIN
    select count(*) into n from allData.super_admins;
    IF  n=0  THEN
        insert into allData.super_admins(login, pass) VALUES (''admin'',''admin'');
    END IF;
END;
' LANGUAGE plpgsql;

create or replace function allData.is_question_valid_by_id(test_id bigint)
    returns boolean
as '
declare
    q record;
    r integer;
    l integer;
begin
    select * into q from allData.questions ques where ques.id=test_id;
    select count(*) into r from allData.answers as ans
    where ans.question_id=test_id and ans.correct=true;
    select count(*) into l from allData.answers as ans
    where ans.question_id=test_id and ans.correct=false;
    if r=0 or l=0 then
        return false;
    end if;
    if q.type=''1'' and r!=1 then
        return false;
    end if;
    return true;
end;
' LANGUAGE plpgsql;

create or replace function allData.is_question_valid()
    RETURNS TRIGGER
as '
    BEGIN
        IF (TG_OP = ''DELETE'') THEN
            UPDATE allData.questions as q
            set is_valid=allData.is_question_valid_by_id(old.question_id)
            where q.id = old.question_id;
            RETURN OLD;
        ELSIF (TG_OP = ''INSERT'') THEN
            UPDATE allData.questions as q
            set is_valid=allData.is_question_valid_by_id(new.question_id)
            where q.id = new.question_id;
            RETURN NEW;
        END IF;
        RETURN NULL;
    END;
' LANGUAGE plpgsql;

CREATE or replace TRIGGER is_question_valid_t
    AFTER INSERT OR DELETE ON allData.answers
    FOR EACH ROW EXECUTE
    FUNCTION allData.is_question_valid();

call allData.init();