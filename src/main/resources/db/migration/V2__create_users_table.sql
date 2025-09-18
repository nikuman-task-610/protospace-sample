CREATE TABLE users (
  id SERIAL NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  encrypted_password VARCHAR(255),
  name VARCHAR(100) NOT NULL,
  profile TEXT NOT NULL,
  occupation TEXT NOT NULL,
  position TEXT NOT NULL,
  PRIMARY KEY (id)
);