CREATE TABLE file
(
    id         bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    file_name  character varying NOT NULL,
    created_at timestamp with time zone
);

ALTER TABLE employee
    ADD CONSTRAINT FK_FILE_ED
        FOREIGN KEY (file_id) REFERENCES file (id);