-- filepath: c:\Users\charl\Desktop\CT4030\BankingApp\src\Assets\Database\defaults.sql
-- Insert default admin user

-- First insert into Users table (required due to foreign key constraint)
INSERT INTO Users (username, password, email, pin)
VALUES ('admin', 'Pass', 'admin@bankingapp.com', 1234);

-- Then insert into Admins table
INSERT INTO Admins (username)
VALUES ('admin');