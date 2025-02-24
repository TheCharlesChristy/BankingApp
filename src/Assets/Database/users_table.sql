-- Create Users Table
CREATE TABLE IF NOT EXISTS Users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    email TEXT NOT NULL,
    pin INTEGER NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);