# LiteCloud

LiteCloud 是一个轻量级的云存储系统，基于 Spring Boot 开发，提供文件存储、用户管理等功能。采用现代化的深色主题设计，支持响应式布局，提供舒适的用户体验。

## 界面预览

### 登录界面
深色主题设计，支持移动端适配
![登录界面](https://github.com/user-attachments/assets/f9594f85-eb07-4b7b-9fb8-1ab2cc4d27d9)

### 文件管理界面
层级化文件管理，操作便捷
![文件管理界面](https://github.com/user-attachments/assets/e5b55b0e-6974-49d5-b9c9-92b8eef8d890)

## 功能特点

- 🔐 用户认证与授权
  - 基于 Spring Security 的安全认证
  - 多角色支持（管理员/普通用户）
  - 会话管理

- 📂 文件管理
  - 文件上传/下载
  - 文件夹创建与管理
  - 文件层级结构
  - 每用户独立存储空间

- 👥 用户管理
  - 用户注册
  - 账户管理
  - 管理员控制面板

- 💻 现代化界面
  - 响应式设计
  - 深色主题
  - 移动端适配

## 技术栈

### 后端
- Spring Boot 2.7.6
- Spring Security
- MyBatis-Plus
- MySQL

### 前端
- 原生 JavaScript
- 现代 CSS3
- 响应式设计

## 系统要求

- JDK 17+
- MySQL 5.7+
- Maven 3.6+

## 快速开始

1. 克隆仓库
```bash
git clone https://github.com/Anyuersuper/LiteCloud.git
```

2. 配置数据库
- 创建数据库
```sql
CREATE DATABASE litecloud;
```
- 导入数据库文件
```bash
mysql -u root -p litecloud < mysql/litecloud.sql
```
- 默认管理员账号
```
用户名：admin
密码：admin
```
> 请在首次登录后立即修改默认密码！

3. 配置应用
编辑 `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/litecloud
spring.datasource.username=your_username
spring.datasource.password=your_password
litecloud.file.base-path=your_file_storage_path
```

4. 运行应用
```bash
mvn spring-boot:run
```

访问 http://localhost:8080 即可使用系统。

## 项目结构

```
LiteCloud
├── src/main/java/com/litecloud
│   ├── config/          # 配置类
│   ├── controller/      # 控制器
│   ├── entity/         # 实体类
│   ├── mapper/         # MyBatis mapper
│   ├── service/        # 业务逻辑
│   └── sdk/           # 工具类
├── src/main/resources
│   ├── mapper/        # MyBatis XML
│   ├── static/        # 前端资源
│   └── application.properties
└── mysql/             # 数据库脚本
```

## 主要功能说明

### 文件管理
- 支持文件上传、下载
- 文件夹创建和管理
- 文件预览（支持多种格式）
- 文件权限控制

### 用户系统
- 用户注册和登录
- 角色基础的权限控制
- 管理员控制面板
- 用户空间管理

## 配置说明

主要配置项：

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| server.port | 服务器端口 | 8080 |
| spring.datasource.url | 数据库连接 | jdbc:mysql://localhost:3306/litecloud |
| litecloud.file.base-path | 文件存储路径 | D:/litecloud/files |
| spring.servlet.multipart.max-file-size | 最大文件大小 | 50MB |

## 安全说明

- 所有API都经过认证和授权
- 文件存储使用用户ID隔离
- 密码经过加密存储
- 防止跨站请求伪造（CSRF）
- 会话管理和控制

## 贡献指南

欢迎提交 Pull Request 或 Issue。

## 开源协议

本项目采用 [MIT 许可证](LICENSE)。




