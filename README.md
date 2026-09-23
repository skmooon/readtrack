# readtrack

个人阅读记录管理系统：记录想读、在读、已读的书。

当前进度：已完成用户注册与登录（Phase 2）；书籍管理（Phase 3）开发中。

## 技术栈

- Java 17
- Spring Boot 4.1.1
- MyBatis 4.1.0（mybatis-spring-boot-starter）
- MySQL 8.0（开发环境使用 8.0.46）

## 快速开始

前提：本机已安装 JDK 17 和 MySQL 8.0。

1. 建库。在 MySQL 客户端里执行：`CREATE DATABASE readtrack DEFAULT CHARACTER SET utf8mb4;`

2. 建表。执行仓库里的建表脚本：`source src/main/resources/schema.sql`。该脚本创建 users 表；重复执行会报 1050（表已存在），属正常现象。

3. 配置数据库密码。项目不保存明文密码，从环境变量读取。在 IDEA 里打开 Run → Edit Configurations → 选中 ReadtrackApplication，在 Environment variables 一栏填 `DB_PASSWORD=你的数据库密码`。

4. 启动。IDEA 里直接 Run `ReadtrackApplication`；或在项目根目录执行 `./mvnw spring-boot:run`（首次执行会下载 Maven，需要联网）。启动成功后控制台出现 Tomcat started on port 8080。

5. 验证。浏览器打开 `http://localhost:8080/hello`，返回 `{"message":"Hello, World!"}` 说明服务已跑通。

## 接口一览

| 方法 | 路径 | 说明 | 请求体 |
| --- | --- | --- | --- |
| GET | /hello | 连通性检查 | 无 |
| POST | /user/register | 用户注册 | {"username":"...","password":"..."} |
| POST | /user/login | 用户登录 | {"username":"...","password":"..."} |
| GET | /users | 用户列表，开发调试用 | 无 |

注册与登录的成功响应为 HTTP 200 加 body 里 code 200；业务失败（用户名重复、密码错误等）返回 HTTP 200 加 body 里 code 400 与中文提示。

## 数据库配置

| 项 | 值 |
| --- | --- |
| 库名 | readtrack |
| 地址 | localhost:3306 |
| 用户名 | root |
| 密码 | 环境变量 DB_PASSWORD |
| 字符集 | utf8mb4 |

连接信息写在 `src/main/resources/application.properties`，由 spring.datasource.url、spring.datasource.username、spring.datasource.password 三行指定；表结构定义在 `src/main/resources/schema.sql`。
