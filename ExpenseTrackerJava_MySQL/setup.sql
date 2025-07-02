-- SQL Script to set up Expense Tracker DB
CREATE DATABASE IF NOT EXISTS ExpenseDB;

USE ExpenseDB;

CREATE TABLE IF NOT EXISTS expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    category VARCHAR(50) NOT NULL,
    amount DOUBLE NOT NULL,
    note TEXT
);
