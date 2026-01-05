CREATE DATABASE IF NOT EXISTS btsconcertdb;
USE btsconcertdb;

CREATE TABLE users (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 username VARCHAR(100) UNIQUE NOT NULL,
 password VARCHAR(255) NOT NULL,
 role VARCHAR(20) NOT NULL,
 full_name VARCHAR(200)
);

CREATE TABLE concerts (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 title VARCHAR(255),
 venue VARCHAR(255),
 city VARCHAR(100),
 date_time DATETIME,
 vip_price DECIMAL(10,2),
 general_price DECIMAL(10,2),
 vip_capacity INT,
 general_capacity INT,
 description TEXT
);

CREATE TABLE tickets (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 user_id BIGINT,
 concert_id BIGINT,
 seat_type VARCHAR(20),
 price DECIMAL(10,2),
 booking_time DATETIME,
 status VARCHAR(20),
 payment_id VARCHAR(200),
 FOREIGN KEY (user_id) REFERENCES users(id),
 FOREIGN KEY (concert_id) REFERENCES concerts(id)
);

INSERT INTO users (username, password, role, full_name)
VALUES ('manager', '$2a$10$Dow1HzdCgE7Y4VYV9XOCFOMqS7FZ2iQWLPxZXcGvR/JD6zqVmkb8y', 'MANAGER', 'Concert Manager');

INSERT INTO concerts (
    id, title, city, venue, date_time,
    description, general_capacity, general_price,
    vip_capacity, vip_price
) VALUES
(1, 'BTS World Tour: Purple Galaxy', 'Seoul', 'Olympic Stadium', '2026-01-15 19:00:00',
 'A spectacular night with BTS in their hometown.', 50000, 120.00, 5000, 300.00),

(2, 'BTS World Tour: Purple Galaxy', 'Tokyo', 'Tokyo Dome', '2026-03-05 18:30:00',
 'BTS takes over Tokyo with an unforgettable performance.', 55000, 130.00, 6000, 320.00),

(3, 'BTS World Tour: Purple Galaxy', 'Los Angeles', 'SoFi Stadium', '2026-05-10 20:00:00',
 'The Kings of K-Pop return to LA for a massive show.', 70000, 150.00, 7000, 350.00),

(4, 'BTS World Tour: Purple Galaxy', 'London', 'Wembley Stadium', '2026-09-01 19:30:00',
 'BTS lights up Wembley with iconic performances.', 60000, 140.00, 6500, 330.00),

(5, 'BTS World Tour: Purple Galaxy', 'New York', 'Madison Square Garden', '2026-10-20 20:00:00',
 'A sold-out night of music, dance, and passion.', 20000, 160.00, 2500, 380.00);
 
 SELECT * FROM user;
 UPDATE users SET role = 'ROLE_MANAGER' WHERE role = 'MANAGER';

SELECT username, role FROM users; 
UPDATE users SET role = 'ROLE_MANAGER' WHERE username = 'manager';



