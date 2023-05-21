CREATE TABLE employee
(
    id                          bigint            PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    full_name                   VARCHAR(355),
    gender                      INTEGER,
    role_in_event               VARCHAR(600),
    competence_list             character varying,
    position                    character varying,
    category                    VARCHAR(1024),
    employment_start            DATE,
    workplace                   VARCHAR(2048),
    profession                  VARCHAR(1024),
    date_of_birth               DATE,
    employment_start_at_rosatom DATE,
    education                   VARCHAR(3000),
    education_place             VARCHAR(3000),
    graduation_year             INTEGER,
    specialization              VARCHAR(3000),
    file_id                     bigint
);