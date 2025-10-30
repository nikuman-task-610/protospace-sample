ALTER TABLE prototypes ADD COLUMN user_id INT;

ALTER TABLE prototypes ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id);