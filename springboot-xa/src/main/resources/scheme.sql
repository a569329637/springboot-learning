
CREATE DATABASE jtm1;
USE jtm1;
DROP TABLE IF EXISTS person;
CREATE TABLE person (
  id int(11) NOT NULL,
  name varchar(255) DEFAULT NULL,
  age int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE DATABASE jtm2;
USE jtm2;
DROP TABLE IF EXISTS person;
CREATE TABLE person (
  id int(11) NOT NULL,
  name varchar(255) DEFAULT NULL,
  age int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;
