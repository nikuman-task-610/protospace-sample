# テーブル設計

## users テーブル

| カラム名            | データ型       | 制約             |
| ------------------ | ------------ | ---------------- |
| id                 | SERIAL       | PRIMARY KEY      |
| email              | VARCHAR(255) | NOT NULL, UNIQUE |
| encrypted_password | VARCHAR(255) | NOT NULL         |
| name               | VARCHAR(100) | NOT NULL         |
| profile            | TEXT         | NOT NULL         |
| occupation         | TEXT         | NOT NULL         |
| position           | TEXT         | NOT NULL         |

### アソシエーション:

- @OneToMany Prototypes
- @OneToMany Comments

## prototypes テーブル

| カラム名    | データ型       | 制約                  |
| ---------- | ------------ | --------------------- |
| id         | SERIAL       | PRIMARY KEY           |
| title      | VARCHAR(100) | NOT NULL              |
| catch_copy | TEXT         | NOT NULL              |
| concept    | TEXT         | NOT NULL              |
| image_name | VARCHAR(255) | NOT NULL              |
| image_type | VARCHAR(100) | NOT NULL              |
| image_data | BYTEA        | NOT NULL              |
| user_id    | REFERENCES   | NOT NULL, FOREIGN KEY |

### アソシエーション:

- @OneToMany Comments
- @ManyToOne User

## comments テーブル

| カラム名      | データ型     | 制約                  |
| ------------ | ---------- | --------------------- |
| id           | SERIAL     | PRIMARY KEY           |
| content      | TEXT       | NOT NULL              |
| prototype_id | REFERENCES | NOT NULL, FOREIGN KEY |
| user_id      | REFERENCES | NOT NULL, FOREIGN KEY |

### アソシエーション:

- @ManyToOne User
- @ManyToOne Prototype