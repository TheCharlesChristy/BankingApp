-- Create Admins Table
CREATE TABLE IF NOT EXISTS Admins (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    FOREIGN KEY (username) REFERENCES Users(username)
);