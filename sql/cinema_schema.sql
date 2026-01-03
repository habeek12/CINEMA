-- ======================================================
--  CINEMA - ESQUEMA FINAL (CORREGIDO Y FUNCIONAL)
--  Base de datos: cinematrailer
-- ======================================================

USE cinematrailer;

-- ======================================================
--  ROLES
-- ======================================================
CREATE TABLE roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  description VARCHAR(255),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ======================================================
--  USERS
-- ======================================================
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  email VARCHAR(150) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(150),
  phone VARCHAR(30),
  enabled TINYINT(1) DEFAULT 1,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  last_login DATETIME NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ======================================================
--  USER_ROLES (N:N)
-- ======================================================
CREATE TABLE user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY(user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ======================================================
--  MOVIES
-- ======================================================
CREATE TABLE movies (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  release_date DATE,
  description TEXT,
  genre VARCHAR(100),
  duration_minutes INT,
  language VARCHAR(50),
  rating VARCHAR(10),
  poster_url VARCHAR(512) DEFAULT '/img/default_poster.png',
  active TINYINT(1) DEFAULT 1,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ======================================================
--  THEATERS
-- ======================================================
CREATE TABLE theaters (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  total_seats INT NOT NULL,
  description VARCHAR(255),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ======================================================
--  SEATS
-- ======================================================
CREATE TABLE seats (
  id INT AUTO_INCREMENT PRIMARY KEY,
  theater_id INT NOT NULL,
  row_label VARCHAR(5) NOT NULL,
  seat_number INT NOT NULL,
  seat_type VARCHAR(50) DEFAULT 'standard',
  UNIQUE KEY ux_seat (theater_id, row_label, seat_number),
  FOREIGN KEY (theater_id) REFERENCES theaters(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ======================================================
--  SHOWTIMES
-- ======================================================
CREATE TABLE showtimes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  movie_id INT NOT NULL,
  theater_id INT NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  price DECIMAL(8,2) NOT NULL DEFAULT 50.00,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE,
  FOREIGN KEY (theater_id) REFERENCES theaters(id) ON DELETE CASCADE,
  INDEX idx_showtime_movie_start (movie_id, start_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ======================================================
--  BOOKINGS
-- ======================================================
CREATE TABLE bookings (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NULL,
  showtime_id INT NOT NULL,
  total_price DECIMAL(10,2) NOT NULL,
  currency VARCHAR(5) NOT NULL DEFAULT 'MXN',
  status ENUM('PENDING','CONFIRMED','CANCELLED','EXPIRED') NOT NULL DEFAULT 'PENDING',
  booking_reference VARCHAR(50) UNIQUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  reserved_at DATETIME NULL,
  cancelled_at DATETIME NULL,
  INDEX idx_bookings_user_created (user_id, created_at),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
  FOREIGN KEY (showtime_id) REFERENCES showtimes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ======================================================
--  PAYMENTS
-- ======================================================
CREATE TABLE payments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  booking_id INT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  method ENUM('CARD','CASH','PAYPAL','TRANSFER'),
  status ENUM('PENDING','PAID','FAILED','REFUNDED') NOT NULL DEFAULT 'PENDING',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ======================================================
--  BOOKING_SEATS
-- ======================================================
CREATE TABLE booking_seats (
  id INT AUTO_INCREMENT PRIMARY KEY,
  booking_id INT NOT NULL,
  showtime_id INT NOT NULL,
  seat_id INT NOT NULL,
  price DECIMAL(8,2) NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
  FOREIGN KEY (showtime_id) REFERENCES showtimes(id) ON DELETE CASCADE,
  FOREIGN KEY (seat_id) REFERENCES seats(id) ON DELETE CASCADE,
  UNIQUE KEY ux_booking_seat (showtime_id, seat_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ======================================================
--  ÍNDICES
-- ======================================================
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_movies_title ON movies(title);
CREATE INDEX idx_booking_reference ON bookings(booking_reference);
CREATE INDEX idx_booking_seats_booking ON booking_seats (booking_id);
CREATE INDEX idx_bookings_showtime ON bookings(showtime_id);

-- ======================================================
--  INSERTAR ROLES
-- ======================================================
INSERT INTO roles (name, description) VALUES
('ADMIN', 'Administrador del sistema'),
('USER', 'Usuario final / cliente');

-- ======================================================
--  INSERTAR USUARIOS (BCrypt)
--  admin123 / usuario123
-- ======================================================
INSERT INTO users (username, email, password, full_name) VALUES
(
 'admin',
 'admin@cinema.local',
 '$2a$10$7O8pQhU7G8VYFLKk3Clbeu7qiljOEGVt3C7vHaEJmVlnCiwjnUHZC',
 'Administrador Cinema'
),
(
 'juan',
 'juan@example.com',
 '$2a$10$682JR76NxW1R74G8ssnBqeE4YjL6rF.5Y2uAmDwwpmHq8Hywes5LK',
 'Juan Pérez'
);

-- ======================================================
--  ASIGNAR ROLES
-- ======================================================
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u JOIN roles r
WHERE u.username='admin' AND r.name='ADMIN';

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u JOIN roles r
WHERE u.username='juan' AND r.name='USER';

-- ======================================================
--  MOVIES
-- ======================================================
INSERT INTO movies (title, description, genre, duration_minutes, language, rating) VALUES
('The Great Escape', 'Acción y aventura', 'Action', 125, 'English', 'PG-13'),
('La Ciudad Perdida', 'Aventura y comedia romántica', 'Adventure', 102, 'Spanish', 'PG-13'),
('Space Odyssey', 'Ciencia ficción épica', 'Sci-Fi', 142, 'English', 'PG-13');

-- ======================================================
--  THEATERS
-- ======================================================
INSERT INTO theaters (name, total_seats, description) VALUES
('Sala 1', 40, 'Sala principal - 40 asientos'),
('Sala 2', 30, 'Sala secundaria - 30 asientos');

-- ======================================================
--  GENERAR ASIENTOS
-- ======================================================
INSERT INTO seats (theater_id, row_label, seat_number)
SELECT t.id, r.row_label, s.seat_number
FROM theaters t
JOIN (SELECT 'A' row_label UNION SELECT 'B' UNION SELECT 'C' UNION SELECT 'D') r
JOIN (SELECT 1 seat_number UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
      UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) s
WHERE t.name='Sala 1';

INSERT INTO seats (theater_id, row_label, seat_number)
SELECT t.id, r.row_label, s.seat_number
FROM theaters t
JOIN (SELECT 'A' row_label UNION SELECT 'B' UNION SELECT 'C') r
JOIN (SELECT 1 seat_number UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
      UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) s
WHERE t.name='Sala 2';

-- ======================================================
--  SHOWTIMES
-- ======================================================
INSERT INTO showtimes (movie_id, theater_id, start_time, end_time, price) VALUES
(
 (SELECT id FROM movies WHERE title='The Great Escape'),
 (SELECT id FROM theaters WHERE name='Sala 1'),
 '2025-11-20 17:00:00', '2025-11-20 19:05:00', 80.00
),
(
 (SELECT id FROM movies WHERE title='Space Odyssey'),
 (SELECT id FROM theaters WHERE name='Sala 1'),
 '2025-11-20 20:00:00', '2025-11-20 22:22:00', 100.00
),
(
 (SELECT id FROM movies WHERE title='La Ciudad Perdida'),
 (SELECT id FROM theaters WHERE name='Sala 2'),
 '2025-11-20 18:00:00', '2025-11-20 19:42:00', 60.00
);
