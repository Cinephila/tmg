-- File: schema.sql
-- Run this in your MySQL database to create required tables.

CREATE TABLE IF NOT EXISTS account_deletion_requests (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  username VARCHAR(255),
  reason TEXT,
  requested_at DATETIME NOT NULL,
  status VARCHAR(50) NOT NULL DEFAULT 'PENDING'
);

CREATE TABLE IF NOT EXISTS contact_messages (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  subject VARCHAR(255) NOT NULL,
  message TEXT NOT NULL,
  submitted_at DATETIME NOT NULL,
  status VARCHAR(50) NOT NULL DEFAULT 'NEW'
);
