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

以下の手順によって利用できる。

## ① アプリ画面の上部右端にある「新規登録」からユーザーの新規登録ページに移動し、必要な情報をフォームに入力して送信する。既にアカウントを登録しているまたはテスト用アカウントを使ってログインする場合は、次の②を閲覧すること。

### <font color="Red">※ただし、アプリの仕様によりアカウントの新規作成に成功しても初回はログインしていない状態になる。そのため、次の②を確認すること。</font>
### <font color="Red">※全ての入力欄に情報が入力されていなければ送信できないので情報は漏れなく入力すること。</font>

## ② アプリ画面の上部右端にある「ログイン」からログインページに移動し、必要な情報をフォームに入力してログインする。

### 以下に、用途別の利用方法について説明している。該当する箇所を閲覧していただきたい。

## <details><summary>ユーザー詳細ページを閲覧する</summary>

## ① ホーム画面またはアプリの試作品情報に記載されているユーザーの名前をクリックする。
</details>

## <details><summary>試作品詳細ページを閲覧する</summary>

## ① ホーム画面またはユーザー詳細ページに記載されているアプリの試作品の画像か題名をクリックする。
</details>

## <details><summary>アプリの試作品情報を投稿する</summary>

## ① ログインした状態で、アプリ画面の上部右端にある「New Proto」から新規投稿ページに移動する。

## ② 必要な情報をフォームに入力して送信する。

### <font color="Red">※全ての入力欄に情報が入力されていなければ投稿できないので情報は漏れなく入力すること。</font>
</details>

## <details><summary>アプリの試作品情報を編集する</summary>

## ① ログインした状態で、ホーム画面またはユーザー詳細ページにある編集したいアプリの試作品の画像や題名をクリックし、詳細ページに移動する。

## ② 詳細ページ内の画像の上にある「編集する」ボタンをクリックし、編集画面に移動する。

## ② 編集したい内容を入力欄に加え、送信する。編集に成功すると詳細ページに戻る。

### <font color="Red">※編集ページを開いた時、添付している画像は表示されない仕様になっているため注意すること。</font>
</details>

## <details><summary>アプリの試作品情報を削除する</summary>

## ① ログインした状態で、ホーム画面またはユーザー詳細ページにある削除したいアプリの試作品の画像や題名をクリックし、詳細ページに移動する。

## ② 詳細ページ内の画像の上にある「削除する」ボタンをクリックし、削除する。
</details>

## <details><summary>コメントを投稿する</summary>

## ① ログインした状態で、コメントしたい試作品の詳細ページへ移動する。

## ② 必要な情報をコメント投稿欄に入力して送信する。
</details>

# 目指した課題解決

（例）アプリ開発に携わるエンジニアが、各々のアプリの試作品についての情報とフィードバックしてもらった情報を一括でまとめられるツールがない課題を解決しようとしている。

# 洗い出した要件


# 実装した機能についての画像やGIFおよびその説明


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