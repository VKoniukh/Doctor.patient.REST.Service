CREATE TABLE IF NOT EXISTS doctors (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name       VARCHAR(50)   NOT NULL,
    last_name        VARCHAR(100)  NOT NULL,
    patronymic      VARCHAR(1000),
    position        VARCHAR(100)  NOT NULL,
    birth_date       DATE          NOT NULL,
    phone_number     BIGINT        NOT NULL
    );

CREATE TABLE IF NOT EXISTS patients (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    FOREIGN KEY (doctor_id)  REFERENCES doctors (Id),
    doctor_id        BIGINT,
    first_name       VARCHAR(50)     NOT NULL,
    last_name        VARCHAR(100)    NOT NULL,
    patronymic      VARCHAR(1000),
    birth_date       DATE            NOT NULL,
    phone_number     BIGINT          NOT NULL
    );





