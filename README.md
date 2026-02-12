# アプリケーション名

## ProtoSpace

# アプリケーション概要

以下の2点のことができる、アプリ開発時のフィードバック用アプリである。
- 使用者は自身の基本情報（名前、プロフィール、所属、役職）と自身のアプリの試作品を管理できる。
- 使用者は新規登録後、画像付きでアプリの試作品情報を投稿できる。
- 投稿内容は編集、削除ができる。
- 投稿にはコメントが可能であり、他のユーザーからフィードバックを得られる。

# URL

（ここにRenderでデプロイした際のProtospaceのURLを貼り付ける）

# テスト用アカウント

## アカウント情報
| メールアドレス   | パスワード  |
| -------------- | --------- |
| sample@test000 | khkih9069 |

# 利用方法
ユーザー新規登録を行い、ログインした状態にすることで試作品を投稿したり、それらに対してコメントをつけることができる。

# 目指した課題解決

（例）アプリ開発に携わるエンジニアが、各々のアプリの試作品についての情報とフィードバックしてもらった情報を一括でまとめられるツールがない課題を解決しようとしている。

# 洗い出した要件

（例）アプリの試作品ごとにフィードバックを返せるようにしたい。
特定のエンジニアが制作したアプリを一覧で見られるようにしたい。
試作品を制作したエンジニアの情報を、試作品の詳細から見られるようにしたい。
アプリの試作品を投稿できるようにしたい。
試作品の情報を後で修正・削除できるようにしたい・
試作品の投稿や編集、削除などはユーザー登録をしているエンジニアのみが行えるようにしたい。

# 実装した機能についての画像やGIFおよびその説明

ユーザー登録機能
・ユーザー登録を行うことで、試作品の投稿や編集・削除、コメントの投稿が可能になる。
・最初の新規登録を行い、以降はログインをすることでアプリ内の各種機能が利用できるようになる。
・新規登録では以下の情報を記入し、新しいユーザーとして登録を行う。
1. 名前
2. メールアドレス
3. パスワード
4. プロフィール
5. 所属
6. 役職

※ただし、このアプリでは新規登録を行っただけではまだログインした状態にはなっていないため、再度ログインを
してもらう必要がある。

・一方、ログインでは以下の情報を用いて認証を行う。
1. メールアドレス
2. パスワード

試作品投稿機能
・登録したユーザーでログインすることで使用することができる。
・以下の情報を入力することで試作品を投稿できる。
1. 試作品の名称
2. キャッチコピー
3. コンセプト
4. 試作品の画像

試作品詳細機能
・ログイン、ログアウト状態に関わらず閲覧できる。
・試作品の情報が全て掲載しており、投稿したユーザー名をクリックするとそのユーザーに関する詳細ページへ移動できる。
・ログイン状態の場合、ここから試作品の編集・削除が可能。
・投稿されたコメントが一覧で表示されており、コメントを閲覧できる。
・コメントにあるユーザー名からユーザー詳細ページへ移動できる。
・ログインした状態だと、コメント投稿用フォームが表示される。

試作品編集機能
・登録したユーザーでログインすることで使用することができる。
・すでに投稿した試作品の情報を修正することができる。
※編集画面に移った際に画像だけ表示されないが、編集を行わなくても元の画像は保持されているため問題はない
※他のユーザーが投稿した試作品を修正することはできない。

試作品削除機能
・登録したユーザーでログインすることで使用することができる。
・必要がなくなった試作品を投稿したユーザーが削除することができる。
※削除ボタンを押すと警告なしに削除されるため、よく考えて削除すること。

試作品一覧機能
・ログイン、ログアウト状態に関わらず閲覧できる。
・トップページにこれまで投稿されてきた試作品が表示される。
・左上から新しい試作品が表示されるようになっている。
・試作品の名前や画像をクリックすることで試作品の詳細ページへ移動できる。
・試作品に表示されているユーザー名をクリックすると、ユーザーの詳細ページへ移動できる。

ユーザー詳細機能
・ログイン、ログアウト状態に関わらず閲覧できる。
・登録したユーザーの情報と、そのユーザーが投稿した試作品の一覧を閲覧できる。
・投稿された試作品の写真や名前をクリックすることで各試作品の詳細ページへ移動できる。

コメント投稿機能
・登録したユーザーでログインすることで使用することができる。
・特定の試作品に対し、コメントを投稿することができる。

# 実装予定の機能

（例）JavaScriptを用いたコメント投稿の非同期化、投稿検索機能、投稿一覧の表示切り替え機能

# データベース設計

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

# ローカルでの動作方法

## ローカル環境における前提条件
- Java 17以上 (Spring Boot 3.4.9に必要、Java 21があることが望ましい)
- PostgreSQL
- Git

## 手順

### ① リポジトリをクローンする

```bash
git clone https://github.com/ユーザー名/リポジトリ名.git
cd リポジトリ名
```

### ② データベースの準備
PostgreSQLでデータベースを作成する

```bash
# PostgreSQLに接続
psql -U 設定したユーザーネーム

# データベース作成（PostgreSQL内で実行）
CREATE DATABASE 好きなデータベース名;

# 確認後、終了
\q
```

### ③  application.propertiesの設定
src/main/resources/application.propertiesを確認・編集する
※このファイルには機密情報（パスワード）が含まれるため、.gitignoreに追加することを推奨する

```java
// データベース接続情報
spring.datasource.url=jdbc:postgresql://localhost:5432/好きなデータベース名
spring.datasource.username=設定したユーザーネーム
spring.datasource.password=設定したパスワード

// Flyway設定
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

### ④ 4. .gitignoreの確認
.gitignoreに以下が含まれていることを確認する

```java
// application.properties（機密情報を含む場合）
src/main/resources/application.properties

// Gradle
.gradle
build/
!gradle/wrapper/gradle-wrapper.jar

// IDE
.idea/
*.iml
.vscode/
```

### ⑤ Gradleビルド
※初回実行時は依存関係のダウンロードに時間がかかるので注意すること

```java
// Windowsの場合
.\gradlew build

// Mac/Linuxの場合
./gradlew build
```

### ⑥ マイグレーション実行
アプリケーションを起動すると、Flywayが自動的にマイグレーションを実行する

```bash
# Windowsの場合
.\gradlew bootRun

# Mac/Linuxの場合
./gradlew bootRun
```

### ⑦ 動作確認
ブラウザで以下のURLにアクセスする

```http://localhost:8080```