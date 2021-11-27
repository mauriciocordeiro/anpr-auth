DROP TABLE IF EXISTS users;

CREATE TABLE users (
	id INT AUTO_INCREMENT  PRIMARY KEY,
	name VARCHAR(256) NOT NULL,
	surname VARCHAR(256) DEFAULT NULL,
	email VARCHAR(256) DEFAULT NULL,
	username VARCHAR(256) NOT NULL,
	password VARCHAR(256) NOT NULL,
	role VARCHAR(8) NOT NULL,
	token VARCHAR(256) DEFAULT NULL
);

INSERT INTO users(name, username, password, role)
	VALUES ('Admin', 'admin', 'admin', 'ADMIN');