DROP SCHEMA IF EXISTS arcadeapp;
CREATE SCHEMA arcadeApp; 
USE arcadeApp;
DROP TABLE IF EXISTS users; 
CREATE TABLE users
    (username VARCHAR(10), 
    firstname VARCHAR(50), 
    lastname VARCHAR(50),
    email VARCHAR(50), 
    password VARCHAR(250),
    usertype varchar(50),
    PRIMARY KEY (username));