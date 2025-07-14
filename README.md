
# ☁️ LiteCloud

LiteCloud 是一个基于 Spring Boot 的简易网盘系统，支持用户登录、文件上传、下载、目录管理等基础功能。

## ✨ 功能特性
- 👤 用户注册与登录（基于 Spring Security）
- 📤 文件上传、📥 下载、🗑️ 删除
- 📁 目录（文件夹）创建与管理
- 👥 支持多用户隔离
- 📝 文件元数据管理（如文件名、类型、大小、路径等）
- 🖥️ 前端页面：登录页、文件管理页（dashboard）

## 🛠️ 技术栈
- Spring Boot 2.7.6
- Spring Security
- MyBatis-Plus 3.5.7
- MySQL
- Lombok
- 前端：原生 HTML/CSS

## 🚀 快速开始
### 1. 环境准备
- ☕ JDK 17 及以上
- 🐳 Maven 3.6+
- 🐬 MySQL 5.7/8.0

### 2. 数据库配置
在 `src/main/resources/application.properties` 中配置数据库连接：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/litecloud
spring.datasource.username=你的用户名
spring.datasource.password=你的密码
```

### 3. 启动项目
```bash
# Windows
mvnw.cmd spring-boot:run
# 或
mvn spring-boot:run
```
🌐 访问：http://localhost:8080/

### 4. 主要接口说明
- 🔑 `/login` 用户登录（POST，JSON）
- ⬆️ `/files/upload` 文件上传（POST，form-data）
- ⬇️ `/files/downloadFile?id=xxx` 文件下载（GET）
- 📂 `/files/createDir` 创建目录（POST，JSON）

## 📁 目录结构简述
- `controller`：接口层，包含文件与用户相关接口
- `service`：业务逻辑层
- `entity`：实体类（如 Users、Files）
- `mapper`：MyBatis-Plus 映射
- `resources/static`：前端静态页面
- `resources/mapper`：MyBatis XML 映射文件

## ℹ️ 其他
- 📂 文件实际存储路径可在 `application.properties` 配置（`litecloud.file.base-path`）
- 📏 上传文件大小限制可配置

## 📝 License
MIT
