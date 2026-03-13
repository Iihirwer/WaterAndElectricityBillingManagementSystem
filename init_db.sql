-- Create Database
CREATE DATABASE IF NOT EXISTS water_electricity_db;
USE water_electricity_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(255)
);

-- Meters Table
CREATE TABLE IF NOT EXISTS meters (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    meter_number VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(20) NOT NULL,
    assigned_address VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_meter_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Meter Readings Table
CREATE TABLE IF NOT EXISTS meter_readings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reading_date DATE NOT NULL,
    value DOUBLE NOT NULL,
    meter_id BIGINT NOT NULL,
    CONSTRAINT fk_reading_meter FOREIGN KEY (meter_id) REFERENCES meters(id) ON DELETE CASCADE
);

-- Bills Table
CREATE TABLE IF NOT EXISTS bills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    billing_period VARCHAR(50) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    meter_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_bill_meter FOREIGN KEY (meter_id) REFERENCES meters(id) ON DELETE CASCADE,
    CONSTRAINT fk_bill_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Payments Table
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_date DATE NOT NULL,
    amount_paid DECIMAL(19, 2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    bill_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_payment_bill FOREIGN KEY (bill_id) REFERENCES bills(id) ON DELETE CASCADE
);
