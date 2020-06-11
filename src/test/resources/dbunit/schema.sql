CREATE TABLE IF NOT EXISTS company (
  uuid                      VARCHAR(255) NOT NULL,
  name                      VARCHAR(255),
  constraint pk_company primary key (uuid))
;

CREATE TABLE IF NOT EXISTS computer (
  uuid                      VARCHAR(255) NOT NULL,
  name                      VARCHAR(255),
  introduced                TIMESTAMP NULL,
  discontinued              TIMESTAMP NULL,
  company_uuid              VARCHAR(255) DEFAULT NULL,
  CONSTRAINT pk_computer PRIMARY KEY (uuid))
;
