CREATE TABLE workspace_correction(
   id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
   name varchar(2048),
   good_name varchar(1024),
   short_name varchar(355),
   division_id bigint
);

ALTER TABLE workspace_correction
    ADD CONSTRAINT FK_WORK_CORRECTION_DIVISION_ID
        FOREIGN KEY (division_id) REFERENCES division (id);