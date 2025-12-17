CREATE DATABASE studentrecordmanagementsystem;
USE studentrecordmanagementsystem;

CREATE TABLE students (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    dti INT,
    java INT,
    ddca INT
);

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50)
);

