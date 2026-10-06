# readtrack

个人阅读记录管理系统：记录想读、在读、已读的书。

当前进度：Phase2 与 Phase3 均已完成。

## 技术栈

- Java 17
- Spring Boot 4.1.1
- MyBatis 4.1.0（mybatis-spring-boot-starter）
- MySQL 8.0（开发环境使用 8.0.46）

分层结构：Controller（接口层）→ Service（业务层）→ Mapper（数据访问层）→ MySQL。

## 快速开始

前提：本机已安装 JDK 17 和 MySQL 8.0。

1. 建库。在 MySQL 客户端里执行：`CREATE DATABASE readtrack DEFAULT CHARACTER SET utf8mb4;`
2. 建表。执行仓库里的建表脚本：`source src/main/resources/schema.sql`。该脚本创建 users 和 books 两张表；重复执行会报 1050（表已存在），属正常现象。
3. 配置数据库密码。项目不保存明文密码，从环境变量读取。在 IDEA 里打开 Run → Edit Configurations → 选中 ReadtrackApplication，在 Environment variables 一栏填 `DB_PASSWORD=你的数据库密码`。
4. 启动。IDEA 里直接 Run `ReadtrackApplication`；或在项目根目录执行 `./mvnw spring-boot:run`（首次执行会下载 Maven，需要联网）。启动成功后控制台出现 Tomcat started on port 8080。
5. 验证。浏览器打开 `http://localhost:8080/hello`，返回 `{"message":"Hello, World!"}` 说明服务已跑通。

## 接口一览

### 连通性

| 方法  | 路径     | 说明    |
| --- | ------ | ----- |
| GET | /hello | 连通性检查 |

### 用户

| 方法   | 路径             | 说明              | 请求体                                 |
| ---- | -------------- | --------------- | ----------------------------------- |
| POST | /user/register | 用户注册            | {"username":"...","password":"..."} |
| POST | /user/login    | 用户登录            | {"username":"...","password":"..."} |
| GET  | /users         | 用户列表（调试用，不返回密码） | 无                                   |

### 书籍

| 方法     | 路径                   | 说明     | 请求体                                             |
| ------ | -------------------- | ------ | ----------------------------------------------- |
| POST   | /books               | 新增书籍   | {"title":"...","author":"...","totalPages":100} |
| GET    | /books/{id}          | 查询书籍详情 | 无                                               |
| PUT    | /books/{id}/progress | 更新阅读进度 | {"readPages":10}                                |
| DELETE | /books/{id}          | 删除书籍   | 无                                               |
| GET    | /books               | 书籍分页列表 | 无                                               |

新增书籍时 readPages、status、userId 由服务端设置，无需传入。

分页列表的查询参数：page 默认 1、size 默认 10，结果按创建时间倒序。返回的 data 结构为：

```json
{ "list": [], "total": 0, "page": 1, "size": 10 }
```

## 状态与规则

书籍状态由已读页数自动切换，不需要手动指定：

| 已读页数        | 状态          |
| ----------- | ----------- |
| 0           | UNREAD（想读）  |
| 大于 0 且小于总页数 | READING（在读） |
| 等于总页数       | READ（已读）    |

归属校验：查询、更新、删除书籍时会校验该书的 user_id 是否为当前用户，不是则返回「无权操作该书籍」。

关于当前用户：Phase 2 未实现登录态，书籍接口的当前用户固定为 id = 1（题目允许）。

## 响应约定

所有接口统一返回 JSON，结构为 `{"code":业务码,"message":"提示","data":数据}`。

- code 200：业务成功
- code 400：业务失败（如用户名重复、用户名或密码错误、书籍不存在、已读页数越界等），此时 message 为中文提示

HTTP 状态码只表示请求是否送达服务端，业务成败看 body 里的 code。例如查询不存在的书籍返回 HTTP 200 + body 里的 code 400。

## 数据库设计

users 表：

| 字段         | 类型           | 说明     |
| ---------- | ------------ | ------ |
| id         | INT          | 主键，自增  |
| username   | VARCHAR(50)  | 用户名，唯一 |
| password   | VARCHAR(255) | 密码     |
| created_at | TIMESTAMP    | 创建时间   |

books 表：

| 字段          | 类型           | 说明             |
| ----------- | ------------ | -------------- |
| id          | INT          | 主键，自增          |
| title       | VARCHAR(100) | 书名             |
| author      | VARCHAR(50)  | 作者             |
| total_pages | INT          | 总页数            |
| read_pages  | INT          | 已读页数，默认 0      |
| status      | VARCHAR(20)  | 阅读状态，默认 UNREAD |
| user_id     | INT          | 所属用户           |
| created_at  | TIMESTAMP    | 创建时间           |

## 数据库配置

| 项   | 值                |
| --- | ---------------- |
| 库名  | readtrack        |
| 地址  | localhost:3306   |
| 用户名 | root             |
| 密码  | 环境变量 DB_PASSWORD |
| 字符集 | utf8mb4          |

连接信息写在 `src/main/resources/application.properties`，由 spring.datasource.url、spring.datasource.username、spring.datasource.password 三行指定；表结构定义在 `src/main/resources/schema.sql`。
