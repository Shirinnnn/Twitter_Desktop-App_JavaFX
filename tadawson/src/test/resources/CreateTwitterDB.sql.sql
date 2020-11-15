 --This script needs to run only once
DROP DATABASE IF EXISTS TWITTERDB;
CREATE DATABASE TWITTERDB;

USE TWITTERDB;

DROP USER IF EXISTS bob@localhost;
CREATE USER bob@'localhost' IDENTIFIED WITH mysql_native_password  BY 'shirin' REQUIRE NONE;
GRANT ALL ON TWITTERDB.* TO bob@'localhost';

FLUSH PRIVILEGES;
