-- テーブルが既に存在する場合は削除（テストの独立性を保つため）
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS prototypes CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- usersテーブルの作成
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  encrypted_password VARCHAR(255),
  name VARCHAR(100) NOT NULL,
  profile TEXT NOT NULL,
  occupation TEXT NOT NULL,
  position TEXT NOT NULL,
  PRIMARY KEY (id)
);

-- prototypesテーブルの作成
CREATE TABLE prototypes (
    id BIGINT AUTO_INCREMENT NOT NULL,
    title VARCHAR(100) NOT NULL,
    catch_copy TEXT NOT NULL,
    concept TEXT NOT NULL,
    user_id BIGINT,
    image_name VARCHAR(255) NOT NULL,
    image_type VARCHAR(100) NOT NULL,
    image_data BLOB NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- commentsテーブルの作成
CREATE TABLE comments (
  id BIGINT AUTO_INCREMENT NOT NULL,
  content TEXT NOT NULL,
  user_id BIGINT NOT NULL,
  prototype_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (prototype_id) REFERENCES prototypes(id) ON DELETE CASCADE
);