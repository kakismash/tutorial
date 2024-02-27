CREATE TABLE departments
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(100)          NULL,
    `description` VARCHAR(150)          NULL,
    CONSTRAINT pk_departments PRIMARY KEY (id)
);