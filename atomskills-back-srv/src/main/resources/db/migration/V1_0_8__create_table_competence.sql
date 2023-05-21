CREATE TABLE competence
(
    id               bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    file_id          bigint,
    qualification_id bigint,
    data             character varying,
    created_at       timestamp with time zone
);

ALTER TABLE competence
    ADD CONSTRAINT FK_COMPETENCE_FILE_ID
        FOREIGN KEY (file_id) REFERENCES file (id);


ALTER TABLE competence
    ADD CONSTRAINT FK_QUALIFICATION_FILE_ID
        FOREIGN KEY (qualification_id) REFERENCES qualification (id);