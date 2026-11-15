-- Create Database
CREATE DATABASE TrafficManagementDB;

-- Select Database
USE TrafficManagementDB;

-- Create Reports Table
CREATE TABLE reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    location VARCHAR(255) NOT NULL,
    type ENUM('Complaint', 'Incident') NOT NULL,
    category VARCHAR(100) NOT NULL,
    vehicle VARCHAR(20) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert Sample Data
INSERT INTO reports
(name, phone, location, type, category, vehicle, description, status)
VALUES
('Rahul Sharma', '9876543210', 'Jaipur Railway Station',
 'Complaint', 'Illegal Parking', 'RJ14AB1234',
 'Vehicle parked in a no-parking zone', 'PENDING'),

('Amit Kumar', '9876501234', 'MI Road, Jaipur',
 'Incident', 'Road Accident', 'RJ14CD5678',
 'Two vehicles involved in a minor accident', 'PENDING'),

('Priya Singh', '9123456780', 'Tonk Road, Jaipur',
 'Complaint', 'Traffic Signal Violation', 'RJ14EF9012',
 'Vehicle crossed the red traffic signal', 'RESOLVED');

-- Display All Reports
SELECT * FROM reports;
CREATE DATABASE IF NOT EXISTS TrafficManagementDB;

USE TrafficManagementDB;

CREATE TABLE IF NOT EXISTS reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    location VARCHAR(255) NOT NULL,
    type ENUM('Complaint', 'Incident') NOT NULL,
    category VARCHAR(100) NOT NULL,
    vehicle VARCHAR(20) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);CREATE DATABASE IF NOT EXISTS TrafficManagementDB;

USE TrafficManagementDB;

SHOW TABLES;
USE TrafficManagementDB;

SELECT * FROM reports;


USE TrafficManagementDB;

SELECT * FROM reports;

USE TrafficManagementDB;

SELECT * FROM reports;

SHOW TABLES;
