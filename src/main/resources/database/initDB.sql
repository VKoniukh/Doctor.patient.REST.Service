CREATE TABLE IF NOT EXISTS doctors (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstName       VARCHAR(50)   NOT NULL,
    lastName        VARCHAR(100)  NOT NULL,
    patronymic      VARCHAR(1000),
    position        VARCHAR(100)  NOT NULL,
    birthDate       DATE          NOT NULL,
    phoneNumber     BIGINT        NOT NULL
    );

CREATE TABLE IF NOT EXISTS patients (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    FOREIGN KEY (doctorId)  REFERENCES doctors (Id),
    doctorId        BIGINT,
    firstName       VARCHAR(50)     NOT NULL,
    lastName        VARCHAR(100)    NOT NULL,
    patronymic      VARCHAR(1000),
    birthDate       DATE            NOT NULL,
    phoneNumber     BIGINT          NOT NULL
    );

ALTER TABLE doctors RENAME COLUMN firstName TO first_name;
ALTER TABLE doctors RENAME COLUMN lastName TO last_name;
ALTER TABLE doctors RENAME COLUMN phoneNumber TO phone_number;
ALTER TABLE doctors RENAME COLUMN birthDate TO birth_date;

ALTER TABLE patients RENAME COLUMN doctorId TO doctor_id;
ALTER TABLE patients RENAME COLUMN firstName TO first_name;
ALTER TABLE patients RENAME COLUMN lastName TO last_name;
ALTER TABLE patients RENAME COLUMN birthDate TO birth_date;
ALTER TABLE patients RENAME COLUMN phoneNumber TO phone_number;



